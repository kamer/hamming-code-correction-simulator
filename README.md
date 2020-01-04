# Hamming Code Correction Simulator C(7,4)

It's a Hamming Code Correction simulator as the name suggests. It's for my Data Communications lecture assignment. So I intended to achieve just the functionality. I'm gonna refactor this repo some time in the future for code quality.

## Installation Steps
- git clone https://github.com/kamer/hamming-code-correction-simulator.git
- cd hamming-code-correction-simulator 
- mvn install
- java -jar ./target/hamming-code-correction-simulator-0.0.1-SNAPSHOT.jar
- go to localhost:8080

## Flow
- It takes text input.
- Convert input to binary.
- Add parities according to Hamming coding.
- Randomly change 1 bit in 7 bits.
- Show changed (noised) text.
- Find the changed bits and correct them.
