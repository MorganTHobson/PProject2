import matplotlib.pyplot as plt
import numpy as np
import pandas as pd

x = [1,2,4,8,16,32]

y1 = [10607,10326,11128,10327,10617,10326,11324,10329,10622,
      11185,10327,11174,10329,10618,11170,10328,10328,11154,
      10328,10330]
y2 = [5340,5196,5196,5671,5336,5197,5608,5196,5598,5621,5196,
      5329,5328,5197,5639,5197,5331,5197,5198,5197]
y4 = [2604,2817,2929,2941,2606,2691,2940,2942,2939,3210,2922,
      2685,2815,2938,2939,2612,2834,2939,2706,2808]
y8 = [1381,1501,1464,1622,1441,1501,1604,1492,1498,1470,1490,
      1495,1599,1493,1595,1504,1439,1485,1489,1492]
y16 = [864,853,826,856,856,853,965,854,838,834,850,846,866,
       864,831,833,854,856,897,834]
y32 = [825,867,836,858,886,862,865,835,856,822,836,866,843,
       872,847,872,856,908,873,899]

y = [np.median(y1)/np.median(y1),
     np.median(y1)/(np.median(y2)*2),
     np.median(y1)/(np.median(y4)*4),
     np.median(y1)/(np.median(y8)*8),
     np.median(y1)/(np.median(y16)*16),
     np.median(y1)/(np.median(y32)*32)]


plt.plot(x,y)

plt.title('CoinFlip Efficiency')
plt.xlabel('#Threads (n)')
plt.ylabel('Speedup [T(1)/nT(n)]')

plt.xscale('log')

plt.show()
