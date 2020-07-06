# Electoral System Simulator

The goal of this program is to experiment with different voting systems.
Currently, First Past The Post and the Alternative Vote have been implemented. 

> Only one round of voting is implemented now, but in the future I would like
> to add mutations to voters and parties to see how things can differ over time.
> A good example would be how voters for small parties may vote for a larger
> party if they lost the last election by a large margin.

## Model

The model used here is meant to simulate how people's decisions on issues affect
the party they associate themselves with, how the change of parties' stances on
issues over time affect voters, and how election results affect voters.

First, a selection of **issues** are generated. Examples of issues would be Climate
Change, Public Health Care, Abortion, or anything else a political party would
have a strong stance on.

A "stance" on an issue is represented by a **Policy**. These include:
* Strongly Disagree
* Disagree
* Indifferent
* Agree
* Strongly Agree

**Voters** and **parties** are then generated with stances on these issues. A
voter's alignment of parties is determined by how closely they align on issues.

If a voter and a party disagree on over 25% of the issues they take a stance on,
the voter will never vote for that party regardless of the voting system used.

## Voting Systems

Different **Voting Systems** can lead to different results depending on how
close parties' stances on issues are to those of the people.

> Given the random nature of the generation, the results produced here can often
> be **fairly even**, with most parties having fair portion of the votes allocated
> to them. When mutation is added, their difference in voters and parties will
> show over time, and the benefits of different voting systems will start to appear.

### First Past The Post

The person with the most votes wins! This system, implemented in countries such as
the USA, India, Poland,
[and many others](https://en.wikipedia.org/wiki/First-past-the-post_voting#List_of_current_FPTP_countries),
has had much criticism, and can cause issues like the **Spoiler Effect**.

This system is implemented by choosing the closest party each voter aligns with.

### Alternative Vote

> aka Instant-runoff voting

Every voter ranks their favorite parties on their ballot. Until a party has over 50%
of the vote, the party with the lowest result is removed, and the votes towards that
party are redistributed to the next party on that voter's ballot.

## Example Results

These results can be created by running the program. "Voter Spread" represents
how many people had that party as their first, second, or third choice.

> Changing the amount of issues, voters, and parties can all lead to
> different results.


```
Diamond Party Vote Spread: [(0, 3042), (1, 2687), (2, 2236)]
White Party Vote Spread: [(0, 1788), (1, 2333), (2, 2207)]
Ruby Party Vote Spread: [(0, 2480), (1, 2215), (2, 1733)]
Platinum Party Vote Spread: [(0, 3395), (1, 2443), (2, 1962)]
Grey Party Vote Spread: [(0, 2366), (1, 2248), (2, 1829)]
Red Party Vote Spread: [(0, 2135), (1, 2189), (2, 2110)]
Brown Party Vote Spread: [(0, 1653), (1, 1985), (2, 1651)]
Gold Party Vote Spread: [(0, 2924), (1, 2456), (2, 1943)]

First Past The Post Winner: Platinum Party
  Percentage that had winner as their first:     16.0%
  Percentage that had winner in their top two:   29.0%
  Percentage that had winner in their top three: 38.0%
Alternative Vote Winner: Platinum Party
  Percentage that had winner as their first:     16.0%
  Percentage that had winner in their top two:   29.0%
  Percentage that had winner in their top three: 38.0%
```

```
Pink Party Vote Spread: [(0, 1832), (1, 1799), (2, 1855)]
Pearl Party Vote Spread: [(0, 2336), (1, 2460), (2, 2070)]
Blue Party Vote Spread: [(0, 2365), (1, 1876), (2, 1530)]
Black Party Vote Spread: [(0, 3086), (1, 2513), (2, 1724)]
Red Party Vote Spread: [(0, 2694), (1, 2977), (2, 2468)]
Gold Party Vote Spread: [(0, 2963), (1, 2130), (2, 1647)]
Orange Party Vote Spread: [(0, 1351), (1, 1752), (2, 1671)]
Cyan Party Vote Spread: [(0, 3061), (1, 2707), (2, 2142)]

First Past The Post Winner: Black Party
  Percentage that had winner as their first:     15.0%
  Percentage that had winner in their top two:   27.0%
  Percentage that had winner in their top three: 36.0%
Alternative Vote Winner: Cyan Party
  Percentage that had winner as their first:     15.0%
  Percentage that had winner in their top two:   28.0%
  Percentage that had winner in their top three: 39.0%
```
