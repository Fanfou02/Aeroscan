import numpy as np
import argparse
import os
import models.CAE as cae
import utils.ImagesProcessor as ip
import tensorflow as tf
from keras import metrics
from operator import itemgetter
import sys


assert(len(sys.argv)>2)

PATH = sys.argv[1]
trainOrPredict = sys.argv[2]

ratioTrainTest = 0.8
InputShape = np.array([300, 300, 3])
IP = ip.ImagesProcessor()
autoencoder = cae.CAE(InputShape,nbNeuronsLayers=[32, 32, 32], nbConvFilters=(3,3), poolScale=(2, 2))
print(trainOrPredict)
print(PATH)
if(trainOrPredict == "1"):
	directory = os.fsencode(PATH)
	imgs = []
	for file in os.listdir(directory):
		filename = os.fsdecode(file)
		if filename.lower().endswith(".jpg"): 
			img = IP.readImage(PATH + "/" + filename).astype(np.float32)
			img = IP.resizeImage(img, InputShape[:-1])	
			# normalize
			img /= 255.0
			#print(img)
			imgs.append(img)
			print(img.shape)

	imgs = np.array(imgs)
	print(imgs.shape)
	imgs = np.reshape(imgs, (-1, InputShape[0],InputShape[1],InputShape[2]))

	# Randomly split the dataset into training and test subsets
	np.random.shuffle(imgs)
	x_train = imgs[:int(ratioTrainTest*len(imgs))]
	x_test = imgs[int(ratioTrainTest*len(imgs)):]
	print(x_train.shape)
	print(x_test.shape)

	autoencoder.createModel()
	autoencoder.train(x_train, x_test, epochs=300, batch_size=1)
	autoencoder.save('tmp/model_autoencoder.h5')
else:
	autoencoder.load('tmp/model_autoencoder.h5')
	img = IP.readImage(PATH).astype(np.float32)
	img = IP.resizeImage(img, InputShape[:-1])	
	#normalize
	img /= 255.0
	predict = np.squeeze(autoencoder.predict(np.expand_dims(img, axis=0)))
	print(predict)
	if predict == 1:
		os.rename(PATH, PATH[:PATH.find(".jpg")]+ "_NA" + ".jpg");
	else:
		os.rename(PATH, PATH[:PATH.find(".jpg")]+ "_A" + ".jpg");

