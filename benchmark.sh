sh ejecutar.sh -f Ackley -elitismo -benchmark -g 100 -t 1000 -s Torneo -m Normal -desviacion 0.5 -k 3 -tamañoTorneo 2 -d 20 -alpha 0.1 -beta 0.3 -probMutacion 0.01

sh ejecutar.sh -f Griewank -elitismo -benchmark -g 100 -t 1000 -s Torneo -m Normal -desviacion 1.0 -k 3 -tamañoTorneo 2 -d 20 -alpha 0.1 -beta 0.3 -probMutacion 0.01

sh ejecutar.sh -f Rastrigin -elitismo -benchmark -g 100 -t 1000 -s Torneo -m Normal -desviacion 2.0 -k 3 -tamañoTorneo 4 -d 20 -alpha 0.1 -beta 0.3 -probMutacion 0.01

sh ejecutar.sh -f Schwefel -elitismo -benchmark -g 100 -t 2000 -s Torneo -m Normal -desviacion 15 -k 3 -tamañoTorneo 2 -d 20 -alpha 0.1 -beta 0.3 -probMutacion 0.001
