# DueDateCalculator
A simple due date calculator method

This simple app, calculates the due date of a given task. It requires a submission date, and a turnaround time, which defines how many working hours are available for the task. (e.g. 2 days equal 16 hours). 
Working hours are definied between 9AM and 5PM on every working day, Monday to Friday. All holidays are ignored (e.g. A holiday on a Thursday is considered as a 
working day. A working Saturday counts as a non-working day.).

A problem can only be reported during working hours. Any other report's submission date is adjusted to the next working day.

The project contians several junit5 test as well.
