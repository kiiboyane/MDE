# MDE

## Project Description 

### The tool workflow 
![Image of the tool workflow ](https://github.com/kiiboyane/MDE/blob/master/images/workflow.PNG)

###  Illustration of the DC synthesis for the elevator subsystem
![Image of the tool workflow ](https://github.com/kiiboyane/MDE/blob/master/images/ToDC.PNG)

### PlantUMl 
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





