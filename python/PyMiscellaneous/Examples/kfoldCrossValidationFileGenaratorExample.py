'''
Created on Jul 13, 2015

@author: GL26163
'''

import os
from PyMiscellaneous.Statistic import kfold_cross_validation

file1 = open( "H:\\Data\\summer2015\\NewYorkTimes2006\\2006DB_copy.csv","r+")
#file1 = open("H:\\Data\\summer2015\\NewYorkTimes2006\\DataForClassification_PoliticsSportWritersLawEntretainment\\2006nytPeopleInGraphWOIsolated_WLabelDescriptions_WOAmbiguityNotFound&FoundWODescription_AllMetrics_ForClassification.csv", "r+")
#file2 = open("H:\\Data\\summer2015\\NewYorkTimes2007\\DataForClassification_PoliticsSportWritersLawEntretainment\\2007nytPeopleInGraphWOIsolated_WLabelDescriptions_WOAmbiguityNotFound&FoundWODescription_AllMetrics_ForClassification.csv", "r+")
data = kfold_cross_validation.file_subsample_generator(file1, 5, hasHeader= True, savePath= "H:\\Data\\summer2015\\NewYorkTimes2006\\5FoldCrossvalidation\\", saveOrigianl=True)
#kfold_cross_validation.file_subsample_generator(file2, 10, hasHeader= True, savePath= "H:\\Data\\summer2015\\NewYorkTimes2007\\" , saveOrigianl=True)

print("done")