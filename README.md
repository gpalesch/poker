# Poker

## Input Validity Assumptions
In accordance with the exam requirements, this implementation follows these assumptions:
* **No Duplicate Cards:** The program assumes that there are no duplicate cards in the 7 available cards (2 hole cards + 5 board cards).
* **Valid Input:** Input format is assumed to be correct; validation for invalid ranks or suits is not performed.

## Deterministic Output (chosen5)
To ensure the output is deterministic and consistent for testing, the best 5-card hand (`chosen5`) is returned in the following order:
1. **By Importance**: Cards are ordered based on their significance to the hand category (e.g., for Four of a Kind: the four matching cards first, then the kicker).
2. **By Rank**: Within the same importance level (like kickers), cards are ordered by descending rank.
3. **Ace-Low Straight**: For a 5-high straight (the wheel), the order is 5, 4, 3, 2, A.

## Hand Categories and Rules
This evaluator implements the standard ranking (Straight Flush to High Card) and tie-break rules as defined by the official rules reference (Wikipedia).
