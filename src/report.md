# Question 1.1
## 1.1.1)
\(\bigcup_3^*\{a\}^n\cdot\{b\}^* \)

## 1.1.2)
\(\text{Let} \: D = \{ 0, 1, 2, 3, 4, 5, 6, 7, 8, 9 \}\)

\(\text{Let} \: W = \{ '\:', \backslash t, \backslash n \}\)

\(\text{Let} \: S = \{ +, - \}\)

\(\text{Let} \: X^? = \bigcup_0^1 X^n\)

\(S^? \cdot \bigcup_1^\infty D^n \cdot
(\: W^? \cdot \{,\} \cdot W^? \cdot
S^? \cdot \bigcup_1^\infty D^n \:)^*\)

## 1.1.3)
\(\{ a, b \}^4\)

# Question 1.2
## 1.1.1)
`a{3,}b*`

## 1.1.2)
`[+-]?\d+(,\s*[+-]?\d+)*`

## 1.1.3)
`[ab]{4}`

# Question 1.3
![`(a*)|(ab)+|(a?b+)` NFA](res/q1.3.png){ width=65% }

# Question 1.4
| State | a | b | \(\epsilon^*\) |
|:-----:|:-:|:-:|:-----:|
|   A   |   |   | B,C,F |
|   B   |   | B | I     |
|   C   | D |   |       |
|   D   |   | E |       |
|   E   |   |   | C,I   |
|   F   | G |   | G     |
|   G   |   | H |       |
|   H   |   | H | I     |
|  (I)  |   |   |       |

| State | a,\(\epsilon^*\) | b,\(\epsilon^*\) | New Label |
|:-------------:|:-------:|:-----------------:|:---------:|
| (A,B,C,F,G,I) | B,D,G,I | H,I               | A         |
| (B,D,G,I)     | B,I     | E,C,H,I           | B         |
| (H,I)         |         | H,I               | G         |
| (B,I)         | B,I     |                   | C         |
| (E,C,H,I)     | D       | H,I               | D         |
| D             |         | C,E,I             | E         |
| (C,E,I)       | D       |                   | F         |

![`(a*)|(ab)+|(a?b+)` DFA](res/q1.4.png){ width=70% }

# Question 2
Note: As the instructions were unclear regarding newlines, I assumed that any newline in a string file demarks the end of the string. Additionally, I assumed that states are represented by positive integers in the dfa file.
