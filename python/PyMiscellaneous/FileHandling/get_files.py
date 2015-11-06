'''
Created on Jul 16, 2015

@author: GL26163
'''
def getFilesWithExt(ext, directory):
    from os import walk
    
    result = []
    for (dirpath, dirnames, filenames) in walk(directory):
        for file in filenames:
            if file.endswith(ext):
                result.append(file)
        
    return result
    