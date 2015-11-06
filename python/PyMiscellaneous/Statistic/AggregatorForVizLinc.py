'''

This class will work just for file containing a header and name pk and 4 metrics, the graph have to represent an non isolated graph, if not division by zero would happend
Created on Jul 16, 2015

@author: GL26163
'''
from pyparsing import delimitedList

def get_aggregator_from_graphfile(graphfile, metricfile, outputfilepath = None):
    import xml.etree.ElementTree as et
    
    NODE_TAG = "{http://graphml.graphdrawing.org/xmlns}node"
    GRAPH_TAG = "{http://graphml.graphdrawing.org/xmlns}graph"
    EDGE_TAG = "{http://graphml.graphdrawing.org/xmlns}edge"
    DATA_TAG = "{http://graphml.graphdrawing.org/xmlns}data"
    
    tree = et.parse(graphfile)
    root = tree.getroot()
    
    print('uploading data')
    id2lbl, lbl2id = get_id2label_label2id_dic(tree) #all entities in graph file
    
    entities_data, header = uploadMetricsValue(metricfile, lbl2id, True) #all entities in file
    
    print('calculating aggregators')
    #get information
    for child in root.iter(EDGE_TAG):
        #print(child.attrib)
        node1, node2 = child.attrib.get('source'), child.attrib.get('target')
        
        node1, node2 = entities_data.get(node1), entities_data.get(node2) #can be node in the file graph that are not opn the metric file
      
        if node1 != None and node2 != None:
            node1['neigh'] = int(node1.get('neigh')) + 1
            node2['neigh'] = int(node2.get('neigh')) + 1
            
            vnode1, vnode2 = node1.get('metrics'), node2.get('metrics')
            
            anode1, anode2 = node1.get('agg'), node2.get('agg')
            
            for i in range(0,4): #calculate aggregator
                anode1[i] = float(anode1[i]) + float(vnode2[i])
                anode2[i] = float(anode2[i]) + float(vnode1[i])
        else:
            #del entities_data[child.attrib.get('source')]
            #del entities_data[child.attrib.get('target')]
            pass
    
    #normalize         
    for row in entities_data.values():
        print(row)
        for i in range(0, len(row['agg'])):
            row['agg'][i] = float(row['agg'][i]) / float(row['neigh'])
       
        
        print(row)
    
    #write result to file
    if outputfilepath != None:
        from Examples.miscellaneous import createDir 
        import os.path
        #open file
        createDir(os.path.dirname(outputfilepath))
        file = open(outputfilepath, 'w+')
        #save header
        file.write(header + '\n')
        #if pk not equal -1 add information, for each entity in entities_data
        for key in entities_data:
            if  int(entities_data[key]['pk']) == -1:
                continue
            file.write(id2lbl[key] + ','+ str(entities_data[key]['pk']) + ',' + str(entities_data[key]['agg'][0]) + ',' + 
                       str(entities_data[key]['agg'][1]) + ',' + str(entities_data[key]['agg'][2]) + ',' + str(entities_data[key]['agg'][3] ) + '\n')
            
        #close file
        file.close()
        
    return entities_data
def uploadMetricsValue(file, name2id, hasheader = False):
    from numpy import genfromtxt
    import csv
    
    m_vector = [3, 4, 5,6]
    id2data = {}
    header = None
    with open(file, 'r+') as csvfile:
        reader = csv.reader(csvfile)
        for row in reader:
            if hasheader:
                hasheader = False
                header = row[0] + ',' + row[1] + ',' + row[2]+ ',' +  row[3]+ ',' + row[4]+ ',' + row[5]
                continue
            id2data[name2id.get(row[0])] = {'metrics':[row[2], row[3], row[4], row[5]], 'pk':row[1], 'agg':[0, 0, 0, 0], 'neigh':0}


    return id2data, header

def get_id2label_label2id_dic(tree):
    
    NODE_TAG = "{http://graphml.graphdrawing.org/xmlns}node"
    DATA_TAG = "{http://graphml.graphdrawing.org/xmlns}data"
    
    root = tree.getroot()
    id2label = {}
    label2id = {}   
    #get information
    for child in root.iter(NODE_TAG):
        for nodedata in child.iter(DATA_TAG):
            if nodedata.get("key") == 'label':
                id2label[child.attrib.get('id')] = nodedata.text
                label2id[nodedata.text] = child.attrib.get('id')

    return id2label, label2id