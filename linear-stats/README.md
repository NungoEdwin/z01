# LINEAR-STATS

### Overview

This program calculates the Linear Regression Line and the Pearson Correlation Coefficient from a given data set. The data should be presented in a text file where each line contains a y-value, and the x-values are the line numbers (starting from 0). The program reads the file, performs the statistical calculations, and outputs the results.
How the Program Works:
### 1. Input Data

The input data file (data.txt) consists of multiple lines, where each line has a single integer. These integers represent the y-values in a graph. The x-values are the line numbers, starting from 0.

Example data file:

    189
    113
    121
    114
    145
    110

In this example, the x-values will be 0, 1, 2, 3, 4, 5 corresponding to the y-values 189, 113, 121, 114, 145, and 110.

### 2. Running the Program
1. Install Go on your system if you haven't already.
2. Clone this repository using te following command;
```bash
git clone https://learn.zone01kisumu.ke/git/enungo/linear-stats.git
```
3. Open a terminal and navigate to the directory ;
```bash
cd linear-stats
```
4.To run the program, use the following command:
```bash
$ go run main.go data.txt
```
### 3. Output

The program outputs two key statistics:

Linear Regression Line: This line represents the best fit line for the given data. It has the form y = mx + b, where m is the slope and b is the y-intercept.
Pearson Correlation Coefficient: This is a measure of the linear correlation between the x and y values, ranging from -1 (perfect negative correlation) to 1 (perfect positive correlation).

#### Example output:
```bash
Linear Regression Line: y = -8.742857x + 153.857143
Pearson Correlation Coefficient: -0.5330331012
```
- The values of m (slope) and c (intercept) in the Linear Regression Line have 6 decimal places.
- The Pearson Correlation Coefficient is printed with 10 decimal places.

### 4. Explanation of Linear Regression and Pearson Correlation
Linear Regression Line

Linear regression is a statistical method used to model the relationship between two variables by fitting a straight line through the data. The equation for the line is:

    y=mx+c

Where:

- m is the slope of the line, which shows how much y changes for each unit increase in x.
- c is the y-intercept, the value of y when x is 0.

The slope and intercept are calculated using formulas derived from the data points.
#### Pearson Correlation Coefficient

The Pearson Correlation Coefficient measures the strength of a linear relationship between two variables (x and y). The coefficient (r) is given by the formula:
```bash

      n(Σxy) - (Σx)(Σy)
r = -----------------------------
    √[nΣx² - (Σx)²][nΣy² - (Σy)²]​
```
Where:

- n is the number of data points.
 - Σxy is the sum of the product of each x and y.
- Σx and Σy are the sums of the x and y values.
 - Σx^2 and Σy^2 are the sums of the squares of the x and y values.

The Pearson coefficient ranges from -1 to 1:

- r = 1 indicates a perfect positive correlation.
- r = -1 indicates a perfect negative correlation.
- r = 0 means no correlation.

