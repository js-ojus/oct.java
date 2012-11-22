OCT
===
*Ojus Chemistry Toolkit (OCT)* is an open-source toolkit intended to aid in solving a variety of cheminformatics problems.  Its primary focus shall be on problems pertaining to organic synthesis.

It is created and maintained by Ojus Software Labs Private Limited.  It is being made available under the liberal BSD 2-clause license, to encourage both participation and re-use.

PROPOSED STRUCTURE
------------------
*  The package `octcmd` contains some useful stand-alone programs that can be run from the command line.

*  The package `oct.data` contains the essential classes, including:
   -  `Atom`,
   -  `Bond`,
   -  `Ring`,
   -  `Molecule`,
   -   ... .

*  The package `oct.defs` contains common definitions utilized across the toolkit.
   1.  `PeriodicTable`, which defines a chemical periodic table.
   1.  `FunctionalGroups`, which defines all relevant functional groups.
   1.  Several constants enumerating various chemical properties and configurations.
   1.  ... .

*  The package `oct.lib` contains the algorithmic code.  Property calculators, molecule analyzers, reaction retro-synthesizers, _etc._, reside here.

*  The package `oct.util` contains general utility classes and functions that may be useful across the toolkit.

*  The package `oct.xlate` contains the code to convert molecules and reactions from one format to another.

STATUS
------
The toolkit is currently in its initial stages, and is *not* yet usable.

TECHNOLOGY
----------
The main programming language is Java.  The excellent Guava library from Google is also employed.  The molecule catalogue and reaction rules are stored in a MongoDB instance.

CONTRIBUTING
------------
Contribution by way of comments, reviews, design suggestions, criticism or patches is most welcome, and shall be gratefully acknowledged. Those submitting patches acknowledge that they agree to be bound by the terms in the file LICENSE, available together with this README.
