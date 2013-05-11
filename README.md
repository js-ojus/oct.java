OCT
===
__Ojus Chemistry Toolkit (OCT)__ is a toolkit intended to aid in solving a variety of cheminformatics problems.  Its primary focus shall be on problems pertaining to organic synthesis.

__OCT__ is created and maintained by __Ojus Software Labs Private Limited__.  It is available under two licenses.

* As free software made available under the GNU Affero General Public License version 3 (GNU AGPL), to encourage both participation and re-use.  A copy of GNU AGPL should be available together with this README, in a file named _LICENSE_.

* A commercial license that allows its inclusion or use in closed-source software.  Contact [Ojus Labs](mailto:sales@ojuslabs.com) for inquiries.

PROPOSED STRUCTURE
------------------
*  The package `oct.cmd` contains some useful stand-alone programs that can be run from the command line.

*  The package `oct.common` contains common definitions utilized across the toolkit.
   1.  `PeriodicTable`, which defines a chemical periodic table.
   1.  `FunctionalGroups`, which defines all relevant functional groups.
   1.   Constants enumerating various chemical properties and configurations.
   1.  ... .

*  The package `oct.core` contains the essential classes, including:
   -  `Atom`,
   -  `Bond`,
   -  `Ring`,
   -  `Molecule`,
   -  `Reaction`,
   -  `Route`,
   -  `RouteNode`,
   -   ... .

*  This package also contains the algorithmic code.  Property calculators, molecule analyzers, reaction retro-synthesizers, _etc._, reside here.

*  The package `oct.util` contains general utility classes and functions that may be useful across the toolkit.

*  The package `oct.xlate` contains the code to convert molecules and reactions to (and from) their internal formats from (and to) popular external formats.  Initial support shall be for MDL formats.

STATUS
------
The toolkit is currently in its initial stages, and is _not_ yet usable.

TECHNOLOGY
----------
The main programming language is __Java__.  The toolkit makes extensive use of the elegant __Guava__ library.  The molecule catalogue and the reaction rulebase are proposed to be stored in a no-SQL database instance.

CONTRIBUTING
------------
Participation by way of comments, criticism or feature requests is most welcome, and shall be gratefully acknowledged.  If you are interested in submitting a patch, please also read the file _COPYRIGHT_ present together with this _README_.
