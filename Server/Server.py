import socket
import RPi.GPIO as GPIO
import time
from adafruit_servokit import ServoKit
kit = ServoKit(channels=16)
servoPosFinger = 0;
servoPosUnder = 0;
servoPosUpper = 0;
servoPosBase = 0;

kit.servo[0].angle = servoPosUnder;
kit.servo[1].angle = servoPosUpper;
kit.servo[2].angle = servoPosBase;
kit.servo[3].angle = servoPosFinger;

def handleData(input):
    number = int(input)
    global servoPosFinger
    global servoPosUnder 
    global servoPosUpper 
    global servoPosBase
    global pFinger 
    global pUnder 
    global pUpper 
    global pBase
    minServo = 0
    maxServo = 180
    servoSteps = 30
    if(number>8 or number<0):
        return
    if(number==1):
       if(servoPosUnder+servoSteps<=maxServo):           
           servoPosUnder+=servoSteps;
           kit.servo[0].angle = servoPosUnder;
           
    if(number==4):
       if(servoPosUnder-servoSteps>=minServo):           
           servoPosUnder-=servoSteps;
           kit.servo[0].angle = servoPosUnder;
           
    if(number==8):
       if(servoPosBase+servoSteps<=maxServo):           
           servoPosBase+=servoSteps;
           kit.servo[2].angle = servoPosBase;
        
    if(number==7):
       if(servoPosBase-servoSteps>=minServo):           
           servoPosBase-=servoSteps;
           kit.servo[2].angle = servoPosBase;

    if(number==5):
       if(servoPosFinger+servoSteps<=maxServo):
           servoPosFinger+=servoSteps;  
           kit.servo[3].angle=180;

    if(number==6):
       if(servoPosFinger-servoSteps>=minServo):
           servoPosFinger-=servoSteps;
           kit.servo[3].angle=50;

    if(number==2):
       if(servoPosUpper+servoSteps<=maxServo):
           servoPosUpper+=servoSteps;   
           kit.servo[1].angle = servoPosUpper;

    if(number==3):
       if(servoPosUpper-servoSteps>=minServo):
           servoPosUpper-=servoSteps;          
           kit.servo[1].angle = servoPosUpper;



serv = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
serv.bind(('192.168.178.29', 5556))
serv.listen(5)
print("Server started")

while True:
    conn, addr = serv.accept()
    print ('client connected')
    from_client = ''
    while True:
        data = conn.recv(4096)
        if not data: break
        print (data.decode())
        handleData(data.decode())
    conn.close()
    print ('client disconnected')
    
  

