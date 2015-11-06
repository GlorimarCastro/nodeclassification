'''
Created on Jul 21, 2015

@author: GL26163
'''

def errorrate(output, truth):
    '''
        Return None if the list are not equal in size, if they are equal in size return the error
        
    '''
    if len(output) != len(truth):
        print('Both list have to be same size')
        return None
    
    missClassified = 0
    
    for i in range(0, len(output)):
        if output[i] != truth[i]:
            missClassified = missClassified +1
    
    return float(missClassified / float(len(truth)))
        
        
def selectBestSystem(systemsList):
    bestSystem = {'error' : 101}
    for system in systemsList:
        if float(bestSystem['error'] > float(systemsList[system]['error'])):
            bestSystem = systemsList[system]
        print(systemsList[system]['error'])
        
    return bestSystem