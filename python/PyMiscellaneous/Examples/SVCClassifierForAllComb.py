'''
Created on Jul 16, 2015

@author: GL26163
'''
from itertools import combinations as comb
from Statistic.kfold_cross_validation import get_cv_data as getData
from sklearn.svm import SVC
from sklearn.externals.six import StringIO 
from Statistic.performance import errorrate, selectBestSystem
import numpy
import pydot
import miscellaneous as mic
from _ast import Str

#-------------------------------------------------------------------------------------------
#-------------------------------------------------------------------------------------------
sampleDirectory = "D:\\CrossValidationSamples\\"
outputDirectory = "D:\\CrossValidationSamples\\CrossGraph\\SVC\\try\\"

f_colm = range(1,17) + [  33,34,35,36] #[4,6,8, 33,34,35,36]#range(1,41) #features to extrac from files
pk_colm = 0
class_clmn = 40
trycombinations = True
#-------------------------------------------------------------------------------------------
#------------------------------------------------------------------------------------------- 

f_amnt = len(f_colm)
best_system = {}
systems = {}
metricNames = mic.getMetricNameArray(f_colm)

#Getting Data for testing and training
#the structure of the returning data is dictionary = {fuld num 1 : data, ..., fold num n : data}
print('reading data')
f_training, class_training, f_testing, truth, pk= getData(sampleDirectory, f_colm, class_clmn, pk_colm, True) #(dir,f_clm,c_clm,pk_clm,haspk)
print('data uploaded')
fold_amnt = len(f_training)
counter = 0;
#iterate trough all the combination of the features
if trycombinations:
    start_k = 1
else:
    start_k = f_amnt

for k in range(start_k,2):#f_amnt + 1):
   
    if trycombinations:
        k_comb = numpy.array(list(comb(range(0, f_amnt),k)))
    else:
        k_comb = numpy.array([range(0,f_amnt)]) #this is to run a specifyc system
    #iterate trough all the combination part of choosing k
    for k_comb_i in k_comb: #this is one system
        counter = counter + 1
        
        #to try all combination with text features always
        k_comb_i = numpy.append(k_comb_i,range(f_amnt - 4, f_amnt))
        
        
        #add systems to system dictionary
        systemName = 'System' + str(counter)
        systems[systemName] = {'error' : 0, 'system': k_comb_i, 'name': mic.getMetricName(k_comb_i, metricNames), 'pk': systemName}   
       
        print("working with " + systems[systemName]['name'])      
        #iterate trough all fold
        for fold_x in range(0, fold_amnt):
            #getting data for fold_x
            foldX_f_training, foldX_cass_training, foldX_f_testing, foldX_pk, foldX_truth = f_training[str(fold_x)], class_training[str(fold_x)], f_testing[str(fold_x)], pk[str(fold_x)], truth[str(fold_x)]
            foldX_f_training, foldX_f_testing = foldX_f_training[:, k_comb_i], foldX_f_testing[:, k_comb_i] #select column in combination
            
            #running the decision tree
            clf = SVC()
            clf = clf.fit(foldX_f_training, foldX_cass_training)
            prediction = clf.predict(foldX_f_testing)
            
            #calcular performance 
            error = errorrate(prediction, foldX_truth)
            
            #anadir performance al sistema
            systems[systemName]['error'] = float(systems[systemName]['error']) + error
            
            #guardar fold
            errorstr = "%.2f"%error
            
            systemData = '#System: ' + systems[systemName]['name'] + '\n#Columns from selected metrics: ' + numpy.array_str(systems[systemName]['system']) + '\n#Performance: Error: ' + errorstr
            filepath = outputDirectory + '\\' + systemName + '\\Fold' + str(fold_x) + '\\Fold' + str(fold_x) + '.csv'
            foldDir = mic.saveFold(filepath, systemData, foldX_pk, foldX_truth, prediction)
            
            #save tree
            #dot_data = StringIO()
            #dotFile = foldDir + '\\Fold' + str(fold_x) + '.dot'
            
            
            #tree.export_graphviz(clf, out_file = dot_data)
            
            #dotgraph = pydot.graph_from_dot_data(dot_data.getvalue())
            #dotgraph.write_pdf(foldDir + '\\Fold' + str(fold_x) + '.pdf')
            #dotgraph.write_png(foldDir + '\\Fold' + str(fold_x) + '.png')
            
        #calcular promedio de sistema
        systems[systemName]['error'] = float(systems[systemName]['error']) / float(fold_amnt)
        print(systems[systemName])
        
#seleccionar mejor sistema de los existente

best_system = selectBestSystem(systems)

#guardar datos de mejor sistema
print(best_system)
file = open(outputDirectory + '\\bestsystem.txt', 'w+')
file.write(str(best_system))
file.close()
print("Script end")
