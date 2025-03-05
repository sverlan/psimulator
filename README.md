# PSimulator

## Overview

PSimulator is a command-line simulator for P systems. This tool simulates P system operations based on a user-defined input file containing the system configuration and rewriting rules. Optionally, the simulation can generate a state graph for further analysis.


## Features

- **Command-Line Execution:** Run simulations with a simple command.
- **Flexible Simulation Steps:** Specify the number of steps to run (defaults to 1).
- **Graph Generation:** Optionally generate a state graph to visualize simulation structure.


## Prerequisites

- **Java Runtime Environment:** Ensure you have Java installed on your system. 

## Installation

1. Ensure Java is installed on your system
2. Download the `PSimulator.jar` file
3. Place the jar file in your preferred directory

## Compiling from source

After checkout use `ant compile` and then `ant build`.

## Usage

### Command Line Syntax

```
java -jar PSimulator.jar <input_file> [nb_steps] [graph]
```

#### Parameters
- `input_file`: Required. Configuration and rules file
- `nb_steps`: Optional. Number of simulation steps (default: 1)
- `graph`: Optional. Any string to generate a state graph

### Input File Format

The input file uses a specific structure delimited by dashes:

```
# Comments start with #

Configuration
# Configuration line 

Rules
# Rule definitions
```

#### Configuration Notation
- Multisets are written in sparse notation
- Format: `(node/component_number, multiset_contents)`
- Multiset contents can be:
  - Simple symbols: `a` (equivalent to `[a,1]`)
  - Detailed symbols: `[symbol,occurrence_count]`
- Example `(1,a [b,4])(3, cc)`: a multiset vector having `abbbb` in the first component and `cc` in the third.

- Multiset Examples:
  |Notation|Meaning|
  |--------|-------|
  |(0, A B C)|	Node/component 0 contains one A, one B, and one C.
  |(1, [A,3] B C)|	Node/component 1 contains three As, one B, and one C.
  |(2, [X,5] [Y,2])|	Node/component 2 contains five Xs and two Ys.
  |(3,)|	Node/component 3 is empty.
  
Equivalent representations:
  - A A A is the same as [A,3]
  - B C B is the same as [B,2] C

#### Rule Notation
Each rule follows this format:
```
vector of multisets -> vector of multisets ; {permitting} ; {forbidding}
```
- The **left-hand side (LHS)** specifies the multisets (objects and their quantities) that must be present for the rule to apply.
- The **right-hand side (RHS)** defines how these objects change or move between nodes.
- The **permitting condition** (optional) defines additional multisets that must be present for the rule to apply.
- The **forbidding condition** (optional) prevents the rule from applying if those multisets exist.

### Example Rules

#### Rule 1: Simple Transformation

```
(0, A) -> (1, B) ; {} ; {}
```

- If node `0` contains `A`, it transforms into `B` and moves to node `1`.

#### Rule 2: Multiple Elements Transformation

```
(0, A B) -> (1, C D) ; {} ; {}
```

- If node `0` contains `A` and `B`, they transform into `C` and `D` and move to node `1`.

#### Rule 3: Using Permitting Conditions

```
(0, A) -> (1, B) ; {(2, C)} ; {}
```

- If node `0` contains `A`, it transforms into `B` in node `1`.
- This rule can only apply if node `2` contains `C`.

#### Rule 4: Using Forbidding Conditions

```
(0, A) -> (1, B) ; {} ; {(3, D)}
```

- If node `0` contains `A`, it transforms into `B` in node `1`.
- This rule **does not apply** if node `3` contains `D`.

#### Rule 5: Using Multiple Permitting and Forbidding Conditions

```
(0, A) -> (1, B) ; {(1, B)} ; {(3, D), (3, A)(4, B D)}
```

- If node `0` contains `A`, it transforms into `B` in node `1`.
- This rule can only apply if node `1` contains `B`.
- This rule **does not apply** if node `3` contains `D` or if node `3` contains `A` and node `4` contains `BD`.

---


## Full Example Input File

Below is an example of an input file for a variant of the **Example 1**:

```plaintext
# Example 1 
Configuration

# Initial state of the system
(0, [A,4] )(1, a)(2,b)(3,) (4, )


Rules

#1 Move ab from 1,2 to 4,3
(1, a) (2,b) -> (4,a)(3,b); {} ; {}

#2 Move ab from 4,3 to 1,2
(4, a) (3,b) -> (1,a)(2,b); {} ; {}

#3 Move ab from 4,3 to 2,1
(4, a) (3,b) -> (2,a)(1,b); {} ; {}
```

Below is an example of an input file for a variant of the **Example 2**:

```plaintext
# Example 2
Configuration

# Initial state of the system
(0,AX)(1,B)(2,D)(3,Y)

Rules

# This rule removes the unwanted object Y from Cell 3 so that it does not block later rules.
(3, Y) -> (3, ) ; {} ; {}

# Transform and Move A from Cell 0 to Cell 1
(0, A) -> (1, E) ; {(0, X)} ; {(3, Y)}

#Transform and Move B from Cell 1 to Cell 2
(1, B) -> (2, Z) ; {(1, E)} ; {(0, X)}
```



# Contributing

Contributions are welcome! Feel free to fork the repository and submit pull requests for improvements, bug fixes, or new features.

## License

This project is distributed under the MIT license.
