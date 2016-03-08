'''
Created on Jul 16, 2015

@author: GL26163
'''
from PyMiscellaneous.Statistic.AggregatorForVizLinc import get_aggregator_from_graphfile as aggregator

#graph file have to be just for non isolated nodes
#Metric file need to contain 6 column: Name, pk, Metric1, metric2, metric3, metric4
g_filepath = "H:\\Data\\NYT\\2006nyt_vizlinc\\2006nyt_vizlinc\\2006nyt.openord.graphml"
m_filepath = "D:\\OneDrive\\MITLL_IAP\\MiTLL\summer2015\\summer2015\\NewYorkTimes2006\\textbase\\entityRoles2006.csv"
o_filepath = "D:\\OneDrive\\MITLL_IAP\\MiTLL\summer2015\\summer2015\\NewYorkTimes2006\\textbase\\textbaseaggregator.csv"
aggregator(g_filepath, m_filepath, o_filepath)
print("end")