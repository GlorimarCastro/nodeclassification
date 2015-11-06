'''
Created on Jul 21, 2015

@author: GL26163
'''
from _ast import Str

def createDir(path):
    import os.path
    import os
    #create directory for subsamples
    if not os.path.exists(path):
        os.makedirs(path)
        
    return


def saveFold(filepath, systemData, pk, truth, clasificattion):
    '''
       Return the directory were the file was saved, or None if the list are note the same size
    '''
    import os.path
    #comparar que pk truth y classification leng sean la misma
    if len(pk) != len(truth) != len(clasificattion):
        print("Pk, truth list and classification list have to be the same size")
        return None
    
    #crear director and/or file
    createDir(os.path.dirname(filepath))
    file = open(filepath, 'w+')
    
    #escribir systemData en el file
    file.write(systemData)
    file.write('\npk,truth,classification')
    
    #loop para escribir la data en el file
    for i in range(0, len(pk)):
        file.write('\n' + str(pk[i]) + ',' + str(truth[i]) + ',' + str(clasificattion[i]))
        
    #cerrar archivo
    file.close()
    #devolver directorio donde se guardo la data
    return os.path.dirname(filepath)
    

def saveData2File(file, data):
    pass


def getMetricNameArray(positions):
    names = ['Primary Key',
        'Closeness Centrality',
        'Eigenvector Centrality',
        'PageRank',
        'Weighted Degree',
        'Degree',
        'Betweenness Centrality',
        'Neighbors of Neighbors Centrality',
        'Neighbors of Neighbors Log Centrality',
        'closnesscentrality aggregator',
        'Neighbors betweenesscentrality aggregator', 
        'Neighbors eigencentrality aggregator',
        'Neighbors pageranks aggregator',
        'Neighbors degree aggregator',
        'Neighbors Avrg of Neighbors-Neighbors centrality aggregator',
        'Neighbors Log of Neighbors-Neighbors aggregator'    ,
        'Metrics Avrg of Neighbors weighted degree aggregator',
        'Closeness Centrality Norm',
        'Eigenvector Centrality Norm',
        'PageRank Norm',
        'Weighted Degree Norm',
        'Degree Norm',
        'Betweenness Centrality Norm',
        'Neighbors of Neighbors Centrality Norm',
        'Neighbors of Neighbors Log Centrality Norm',
        'Neighbors closnesscentrality  aggregator Norm',
        'Neighbors betweenesscentrality  aggregator Norm',
        'Neighbors eigencentrality  aggregator Norm',
        'Neighbors pageranks  aggregator Norm',
        'Neighbors degree  aggregator Norm',
        'Neighbors Avrg of Neighbors-Neighbors centrality  aggregator Norm',
        'Neighbors Log of Neighbors-Neighbors  aggregator Norm',
        'Neighbors weighted degree aggregator  Norm',
        'Art Prob',
        'Sport Prob',
        'Politics Prob',
        'Other  Prob',
        'Sport',
        'Politics',
        'Entertainment',
        'Class']
    
    result = []
    
    for i in positions:
        result.append(names[i])
        
            
    return result

def getMetricName(positions, array2mod):
    result = ''
    for i in positions:
        result = result + '    ' + array2mod[i]
        
            
    return result