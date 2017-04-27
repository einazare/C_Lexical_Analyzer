# C_Lexical_Analyzer
A C Lexical Analyzer which devises a .c file into tokens

This is not necesary a C Lexical Analyzer. It should work for any other languages, as long as you give to it a deterministic finite automaton for your language (in file named "automat.txt").
The states "ignora" and "precompilare" were used in my case to ignore whitespaces, new lines, comments etc. You should change these two states with the states you have for ignoring characters. 

Note thate i did not include the automaton for C, and that is because it did not belong to me.
