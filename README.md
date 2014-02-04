OCT
===
__Ojus Chemistry Toolkit (OCT)__ is a toolkit intended to aid in solving a variety of cheminformatics problems.  Its primary focus shall be on problems pertaining to organic synthesis.

__OCT__ is created and maintained by __Ojus Software Labs Private Limited__.  It is available under Apache License 2.0 to both encourage its use and participation of a wider community.  See the file `LICENSE` for more details.

PROPOSED STRUCTURE
------------------
*  The package `oct.cmd` contains some useful stand-alone programs that can be run from the command line.  [_This is empty currently._]

*  The package `oct.common` contains common definitions utilized across the toolkit.
   -  `PeriodicTable`, which defines a chemical periodic table.
   -   Constants enumerating various chemical properties and configurations.
   -  ... .

*  The package `oct.data` contains the essential classes, including:
   -  `Atom`,
   -  `Bond`,
   -  `Ring`,
   -  `Molecule`,
   -  `FunctionalGroup`,
   -  `LeavingGroup`,
   -  `Reaction`,
   -  `Route`,
   -  `RouteNode`,
   -   ... .

*  The package `oct.lib` contains the algorithmic code.  Property calculators, molecule analyzers, reaction retro-synthesizers, _etc._, reside here.  Examples include:
   -  `FunctionalGroups`,
   -  `DefaultRingDetector`,
   -  ... .

*  The package `oct.util` contains general utility classes and functions that are useful across the toolkit.

*  The package `oct.xlate` contains the code to convert molecules and reactions to (and from) their corresponding __OCT__ formats from (and to) popular external formats.  Initial support shall be for popular MDL formats.

STATUS
------
The toolkit is currently in its initial stages, and is _not_ yet usable.

TECHNOLOGY
----------
The main programming language is __Java__.  It may well become the _only_ language used, as well!  Currently, version 1.7 is required to use __OCT__.  In future, we look forward to using 1.8.  The toolkit makes extensive use of the elegant __Guava__ library.  The molecule catalogue and the various kinds of reaction rules are proposed to be stored in a no-SQL database instance.

CONTRIBUTING
------------
Participation by way of comments, (constructive) criticism or feature requests is most welcome, and shall be gratefully acknowledged.  Please read the file `LICENSE` for the terms of Apache License which govern your contributions.
