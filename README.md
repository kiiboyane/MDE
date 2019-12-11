# MDE

## Project Description 

### The tool workflow 
![Image of the tool workflow ](https://github.com/kiiboyane/MDE/blob/master/images/workflow.PNG)

###  Illustration of the DC synthesis for the elevator subsystem
![Image of the tool workflow ](https://github.com/kiiboyane/MDE/blob/master/images/ToDC.PNG)

### PlantUML
the modelisation of the Local Controller (LC) was made with PlantUML  ; saved in the file uml.that 
```
@startuml
(*) --> "0"
"0" --> [<&arrow-top> Go_up] "1"  
"1" --> [<&arrow-bottom> dn] "2"
"2" --> [<&arrow-top> up] "3"
"3" -->  [<&arrow-bottom> Go_up] "4"
"4" --> [<&arrow-top> Go_dn] "5"
"5" --> [<&arrow-bottom> up] "6"
"6" --> [<&arrow-top> dn]  "7"
"7"  --> [<&arrow-bottom> Go_dn] "0"
@enduml
```
### from LCNode to ALCNode to DCNode

All the nodes connected with controllable events are combined to form one ALCNode.A DCNode is contructed using and ALNode plus the conditions 
![Image of the tool workflow ](https://github.com/kiiboyane/MDE/blob/master/images/Nodes.PNG)

### Conditions 
the conditions are saved in the file file.const and written as follow :

```
If Condition1 then Ord(Go_up)
If Condition2 then Ord(Go_dn)
```

### Grafcet 

The final DC is transformed to a Grafcet modelised using json , the result is saved in the file file.json  it can be visualized as follow :
![Image of the tool workflow ](https://github.com/kiiboyane/MDE/blob/master/images/grafcet.PNG)
use this [link](https://gojs.net/2.0.20/samples/grafcet.html) to visualize it.






