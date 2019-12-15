# Progress

## Version
- Version 1.0.1 (15-12-2019 uploaded)

## Compile
```
alias greent='javac -d bin lib/*.java && java -cp bin Main $1'
javac -d bin lib/*.java
java -cp bin Main $1
```

After run the code, we can run greent file by the below way.
```
greent FILENAME.greent
```

## GreenTea File
```
int A = ( ( 10 - 3 ) + 2 + 1 ) , B = 3 / 3 * 2 ;
int C = A - B ;
double D = 1 - 10 ;
int E = ( ( 1 + 2 ) * ( 1 + 2 + 1 * 2 ) ) + 2 * 2 , F = ( ( D - A ) + E ) , G = 5 + 3 * 6 * ( 8 * 2 + 1 ) ;
println ( A ) ;
println ( B ) ;
println ( C ) ;
println ( " Hello World. This is a pen. " ) ; 
```

## Next Step
- In Calculation, without brackets, we ```cannot``` calculate subtraction correctly by using Shunting-yard algorithm.
    - 10 - 3 + 7    $\rightarrow$ 0 (the correct answer is 14). 
    - ( 10 - 3 ) + 7 $\rightarrow$ 14 (the correct answer is 14). 

- I wanna defin functions such as main, getter, setter and toString. 