import numpy as np
import argparse
import os
import models.CAE as cae
import utils.ImagesProcessor as ip
import tensorflow as tf
from keras import metrics
from operator import itemgetter
import sys
import pickle


assert(len(sys.argv)>2)

PATH = sys.argv[1]
trainOrPredict = sys.argv[2]

ratioTrainTest = 0.8
inputShape = np.array([300, 300, 3])
slicesSize = np.array([28, 28, 3])
overlap = 10

IP = ip.ImagesProcessor()
autoencoder = cae.CAE(slicesSize,nbNeuronsLayers=[16, 8, 8], nbConvFilters=(3,3), poolScale=(2, 2))
print(trainOrPredict)
print(PATH)

def euclidienne_distance(data1, data2):    
    return np.sum(np.power(np.array(data1) - np.array(data2), 2))
	

def get_neighbors(training_set, 
              data_point, 
              k, 
              distance=euclidienne_distance):
	distances = []
	for indexTrain in range(len(training_set)):
	    dist = distance(data_point, training_set[indexTrain])
	    distances.append(dist)
	distances.sort()
	neighbors = distances[:k]
	mean_distance = np.mean(neighbors)

	return mean_distance

def loadImages(directory_name):
    directory = os.fsencode(directory_name)
    imgs = []
    filenames = []
    for file in os.listdir(directory):
        filename = os.fsdecode(file)
        if filename.lower().endswith(".jpg"): 
            img = IP.readImage(directory_name + "/" + filename)
            img = IP.resizeImage(img, inputShape[:-1])
            img = IP.extractChromaticity(img)
            img_slices = IP.sliceImage(img,(slicesSize[0], slicesSize[1]),overlap)
            imgs.append(img_slices)
            filenames.append(filename)
    return np.array(imgs), filenames

if(trainOrPredict == "1"):
	# Load references photos
	X_train, trainFilenames = loadImages(PATH)
	# Normalize
	X_train = X_train.astype('float32')/255.0
	print(X_train.shape)

	# Split and train dataset
	x_temp = X_train.reshape(-1, slicesSize[0], slicesSize[1], slicesSize[2])
	x_temp = np.random.permutation(x_temp)
	x_train = x_temp[:int(ratioTrainTest*len(X_train))]
	x_test = x_temp[int(ratioTrainTest*len(X_train)):]
	#autoencoder = cae.CAE(slicesSize,nbNeuronsLayers=[16, 8, 8], nbConvFilters=(3,3), poolScale=(2, 2))
	autoencoder.createModel()
	autoencoder.train(x_train, x_test, epochs=300, batch_size=64)

	autoencoder.save('model_knn.h5')

	# Save the references photos features
	X_train_features = []
	for img in X_train:
	    X_train_features.append(autoencoder.extractFeatures(img))
	X_train_features = np.array(X_train_features)
	print(X_train_features.shape)
	np.save("features_ref", X_train_features)

	# Extract the delta error and save it
	train_distances = []
	for img_index in range(X_train_features.shape[0]):
	    slices_distances = []
	    for slice_img in X_train_features[img_index]:
	        slices_distances.append(get_neighbors(X_train_features.reshape(-1, X_train_features.shape[2], X_train_features.shape[3], X_train_features.shape[4]), slice_img, 5))
	    train_distances.append(np.mean(slices_distances))

	deltaError = np.percentile(train_distances, 75)
	with open('delta_knn.pkl', 'wb') as f: 
		pickle.dump(deltaError, f)

else:
	autoencoder.load('model_knn.h5')
	X_train_features = np.load("features_ref.npy")
	with open('delta_knn.pkl', 'rb') as f: 
		deltaError = pickle.load(f)

	#Extract image
	img = IP.readImage(PATH)
	img = IP.resizeImage(img, inputShape[:-1])
	img = IP.extractChromaticity(img)
	img_slices = IP.sliceImage(img,(slicesSize[0], slicesSize[1]),overlap)

	#normalize
	img_slices = img_slices.astype('float32')/255.0

	#extract featurs
	X_normal_features = []
	for img_slice in img_slices:
	    X_normal_features.append(autoencoder.extractFeatures(np.expand_dims(img_slice, axis=0)))
	X_normal_features = np.array(X_normal_features)
	print(X_normal_features.shape)


	# predict slices
	slices_distances = []
	for slice_img in X_normal_features:
	    slices_distances.append(get_neighbors(X_train_features.reshape(-1, X_train_features.shape[2], X_train_features.shape[3], X_train_features.shape[4]), slice_img, 4))
	mean = np.mean(slices_distances)
	if mean < deltaError:
	    classe = 1
	    os.rename(PATH, PATH[:PATH.find(".jpg")]+ "_NA" + ".jpg");
	else:
	    classe = -1
	    os.rename(PATH, PATH[:PATH.find(".jpg")]+ "_A" + ".jpg");
	print(classe)
		

