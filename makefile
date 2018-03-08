JFLAGS = -g
JC = javac
.SUFFIXES: .java .class
.java.class:
	$(JC) $(JFLAGS) $*.java

CLASSES = \
        BruteForceDES.java \
        CoinFlip.java 

default: classes

classes: $(CLASSES:.java=.class)

clean:
	$(RM) *.class
