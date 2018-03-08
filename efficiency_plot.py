import matplotlib.pyplot as plt
import numpy as np
import pandas as pd

x = [1,2,4,8,16,32]

y1 = []
y2 = []
y4 = []
y8 = []
y16 = []
y32 = []

y = [np.median(y1),
     np.median(y2),
     np.median(y4),
     np.median(y8),
     np.median(y16),
     np.median(y32)]


plt.plot(x,y)

plt.title('CoinFlip Efficiency')
plt.xlabel('#Threads (n)')
plt.ylabel('Speedup [T(1)/nT(n)]')

plt.xscale('log')

plt.show()
