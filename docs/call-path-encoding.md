# Call Path Encoding

Call Paths are used to identify function calls within a call tree. They
are used to indicate to the Cross Blockchain Control contract which
function should be being executed. Only functions that are entry point functions
for a blockchain are counted in the Call Path.

Call Paths are arrays of unsigned integers. The size of the array indicates
how deep the call is in the call tree. If the final element of the array 
is zero, it indicates that the function calls other functions via crosschain calls. 
Otherwise, it indicates that the function does not execute crosschain calls.

This means that the root function call for a call tree has a path encoding of ```{0}```
indicating that it is a function that calls other functions on other blockchains 
via crosschain calls. 

The diagram below shows functions in contracts on blockchains that call 
other contracts. For example, ```function a1a``` on Blockchain A in 
Contract A1 calls function ```function b1a```  on Blockchain B in
Contract B1. This function call could be written: ```function a1a -> function b1a```.

```text
Blockchain A {
  Contract A1 {
    function a1a() {
      calls B.b1.b1a()
    }
    function a1b() {
      calls B.b2.b2a()
      calls C.c1.c1a()
      calls B.b2.b2b()
    }
  }
  Contract A2 {
    function a2a() {
      calls C.c2.c2a()
    }
  }
}

Blockchain B {
  Contract B1 {
    function b1a() {
    }
  }
  Contract B2 {
    function b2a() {
    }
    function b2b() {
      calls C.c1.c1a()
      calls C.c2.c2a()
      calls A.a2.a2a()
    }
  }
}

Blockchain C {
  Contract C1 {
    function c1a() {
    }
  }
  Contract C2 {
    function c2a() {
    }
  }
}

```

Assuming that the root function is A.a1.a1a() and that all functions in the call tree
are called, then the call paths are shown in the table below.

| Function   | Call Path             | Encoded Call Path |
| ---------- | --------------------- | --- |
| A.a1.a1a() | A.a1.a1a()            | {0}       |
| B.b1.b2a() | A.a1.a1a() -> B.b1.b2a() | {1}   |

Assuming that the root function is A.a1.a1b() and that all functions in the call tree 
are called, then the call paths are shown in the table below.

| Function   | Call Path             | Encoded Call Path |
| ---------- | --------------------- | --- |
| A.a1.a1b() | A.a1.a1b()            | {0}       |
| B.b1.b1a() | A.a1.a1a() -> B.b1.b2a() | {1}   |
| C.c1.c1a() | A.a1.a1a() -> C.c1.c1a() | {2}   |
| B.b2.b2b() | A.a1.a1a() -> B.b2.b2b() | {3, 0}   |
| C.c1.c1a() | A.a1.a1a() -> B.b2.b2b() -> C.c1.c1a()| {3, 1}  |
| C.c2.c2a() | A.a1.a1a() -> B.b2.b2b() -> C.c2.c2a()| {3, 2}  |
| A.a2.a2a() | A.a1.a1a() -> B.b2.b2b() -> A.a2.a2a() | {3, 3, 0}  |
| C.c2.c2a() | A.a1.a1a() -> B.b2.b2b() -> A.a2.a2a() -> C.c2.c2a()| {3, 3, 1}  |

