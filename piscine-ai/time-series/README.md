# time-series
This project demonstrates how to perform a **vectorized backtest** using historical Apple Inc. stock data.  
The goal is to simulate a **long-only trading strategy** (buying the stock if a signal predicts price increase)  
and compute the strategy performance using Pandas without explicit loops. 

## Overview 
- Loads historical Apple stock data (`AAPL.csv`).
- Preprocesses the dataset:
  - Converts `Date` to `datetime`
  - Sets `Date` as index
  - Drops missing values
- Computes **future daily returns** using adjusted close prices:
  \[
  R(t) = \frac{P(t+1) - P(t)}{P(t)}
  \]
- Generates random trading signals (`buy` or `do nothing`) with probability 0.5.
- Backtests the strategy:
  - Buys at day *t* if signal=1
  - Sells at day *t+1*
  - Computes daily PnL and total strategy return
- Compares with an **always-buy strategy**.

## Requirements
Make sure you have the following installed:

- Python 3.9+ (tested with 3.12)
- Pandas
- NumPy
- Matplotlib

Install dependencies:

```bash
$ pip install -r requirements.txt
```
## Usage
* Clone this repository

```bash
$ git clone https://learn.zone01kisumu.ke/git/enungo/time-series

cd time-series
```

## How to Run
* Make sure you are inside the virtual environment.
**create virtualenv**
```bash
$ python3 -m venv venv
  source venv/bin/activate   # Linux/Mac
  venv\Scripts\activate      # Windows

# Install dependencies
pip install -r requirements.txt
```

## Contributing
Contributions are welcome! To contribute:

1. Fork this repository
 
2. Create a new branch:

```bash
$ git checkout -b feature-name
```
3. Commit your changes:

```bash
$ git commit -m "Added feature-name"
```
4. Push to your branch:

```bash
$ git push feature-name
```
5. Open  a Pull Request