'''
Created on Jul 13, 2015

@author: GL26163
'''
import os
from distutils.tests.test_text_file import TEST_DATA

def file_subsample_generator(sampleFile, k, hasHeader = False, savePath = os.path.expanduser("~"), saveOrigianl = False):
    '''
        Create k subsample files for validation with its corresponding subsample for training. 
        The subsample files are going to be saved in the user home file, otherwise specified. 
    '''
    if k < 2:
        print("k have to be equal or greater than 2")
        return False
    
    import random
    import math
    
    randomizeXTimes = 100
    resultDirName = "crossValidationSubsamples"
    trainingName = "trainingSubsample"
    testingName = "testSubsample"
    fileExtension = os.path.splitext(sampleFile.name)[1]
    
    
    #handle header
    sampleFile.seek(0,0) #make sure of read since the first line of file
    if hasHeader:
        sampleFile.readline()

    data = sampleFile.read().splitlines() #crate a list with all the lines in the input file
    
    #randomize the data order
    for i in range(randomizeXTimes):
        random.shuffle(data)
        
    #create directory for subsamples
    if not os.path.exists(savePath + "\\" + resultDirName):
        os.mkdir(savePath + "\\" + resultDirName)
    
    #save original
    if saveOrigianl:
        sampleFile.seek(0,0)
        tempFile = open(savePath + "\\" + resultDirName + "\\original" + fileExtension, "w+")
        tempFile.write(sampleFile.read())
        tempFile.close()
        
    #create subsamples
    velidationLines = math.floor(len(data) / float(k))
    startIndex = 0
    stopIndex = int(startIndex + velidationLines)
    for i in range(k):
        validationData = data[startIndex:stopIndex] #extract subsample i for validation
        trainingData = data[:]
        trainingData[startIndex:stopIndex] = []     #delete data used for validation
        startIndex = stopIndex
        stopIndex = int(startIndex + velidationLines)
        #save result to file
        trainingFile = open(savePath + "\\" + resultDirName + "\\" + trainingName + str(i) + fileExtension, "w+")
        testingFile =  open(savePath + "\\" + resultDirName + "\\" + testingName + str(i) + fileExtension, "w+")
        trainingFile.write("\n".join(line for line in trainingData))
        testingFile.write("\n".join(line for line in validationData))
        trainingFile.close()
        testingFile.close()

    
    
    return True



def get_cv_data(dir, f_colm, c_colm, pkcolm = 0, hasPK = False):
    '''
    GET_CV_DATA take as input a directory containing the files for cross validation and return 4 dictionaries.
        F_TRAINING : is a dictionary containing as key the file name number for each fold and the value is a list with all the lines in file
        CLASS_TRAINING : dictionary with fold file names as keys and  all the file lines as values
        F_TEST : dictionary with fold files as key and all the lines in file as value
        
        
        CLASE NO FUNCIONARA PARA FOLDS MAYOR DE 10
    '''
    from PyMiscellaneous.FileHandling.get_files import getFilesWithExt
    from numpy import genfromtxt
    
    #final variables:
    testDirName = "Test\\"
    trainingDirName = "Training\\"
    
    #setting directories:
    trainingDir = dir + trainingDirName
    testDir = dir + testDirName
    trainingFiles = getFilesWithExt(".csv", trainingDir)
    testingFiles = getFilesWithExt(".csv", testDir)
    
    
    #data to return
    f_test = {}
    f_training = {}
    class_training = {}
    pk = {}
    truth = {}
    
    
    #setting dictionaries
    for indx in range(0,len(trainingFiles)):
        
        test_data = genfromtxt(testDir + testingFiles[indx], delimiter = ",")
        training_data = genfromtxt(trainingDir + trainingFiles[indx], delimiter = ",")
        
        testFoldNum = testingFiles[indx][testingFiles[indx].index('.csv') - 1 :testingFiles[indx].index('.csv')]
        trainingFoldNum = trainingFiles[indx][trainingFiles[indx].index('.csv') - 1 :trainingFiles[indx].index('.csv')]
        
        f_test[testFoldNum] = test_data[:,f_colm]
        f_training[trainingFoldNum] = training_data[:,f_colm]
        class_training[trainingFoldNum] = training_data[:,c_colm]
        truth[testFoldNum] = test_data[:,c_colm]

        if hasPK:
            pk[testFoldNum] = test_data[:, pkcolm]
    
    
    return f_training, class_training, f_test, truth, pk




































