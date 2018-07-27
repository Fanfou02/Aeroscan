import os
import numpy as np

import utils.ImagesProcessor as ip
from sklearn import svm
import pickle
import sys


assert(len(sys.argv)>2)

PATH = sys.argv[1]
trainOrPredict = sys.argv[2]

ratioTrainTest = 0.8
InputShape = np.array([300, 300, 3])
IP = ip.ImagesProcessor()
print(trainOrPredict)
print(PATH)

def loadImages(directory_name):
	directory = os.fsencode(directory_name)
	imgs = []
	filenames = []
	for file in os.listdir(directory):
		filename = os.fsdecode(file)
		if filename.lower().endswith(".jpg"): 
			img = IP.readImage(directory_name + "/" + filename)
			img = IP.resizeImage(img, (1000, 1000))
			texture_features = np.array(IP.extractTexturefeatures(img))
			#img = img.reshape(-1)
			imgs.append(texture_features)
			filenames.append(filename)
	return np.array(imgs), filenames

if(trainOrPredict == "1"):
	X_train, trainFilenames = loadImages(PATH)
	X_train = X_train.astype('float32')/X_train.max(axis=0)
	clf = svm.OneClassSVM(nu=0.2, kernel="rbf", gamma=0.2)
	np.random.shuffle(X_train)
	clf.fit(X_train)

	with open('svm_model.pkl', 'wb') as f: 
		pickle.dump(clf, f)
else:
	with open('svm_model.pkl', 'rb') as f: 
		clf = pickle.load(f)

	img = IP.readImage(PATH).astype(np.float32)
	img = IP.readImage(PATH)
	img = IP.resizeImage(img, (1000, 1000))
	texture_features = np.array(IP.extractTexturefeatures(img))
	predict = np.squeeze(clf.predict(np.expand_dims(texture_features, axis=0)))
	print(predict)
	if predict == 1:
		os.rename(PATH, PATH[:PATH.find(".jpg")]+ "_NA" + ".jpg");
	else:
		os.rename(PATH, PATH[:PATH.find(".jpg")]+ "_A" + ".jpg");

