OCT
===
__Ojus Chemistry Toolkit (OCT)__ is a toolkit intended to aid in solving a variety of cheminformatics problems.  Its primary focus shall be on problems pertaining to organic synthesis.

__OCT__ is created and maintained by __Ojus Software Labs Private Limited__.  It is available under two licenses.

* As free software made available under the GNU Affero General Public License version 3 (GNU AGPL), to encourage both participation and re-use.  A copy of GNU AGPL should be available together with this _README_, in a file named _LICENSE_.

* A commercial license that allows its inclusion or use in closed-source software.  Contact [Ojus Labs](mailto:sales@ojuslabs.com) for inquiries.

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
The main programming language is __Java__.  It may well become the _only_ language used, as well!  The toolkit makes extensive use of the elegant __Guava__ library.  The molecule catalogue and the various kinds of reaction rules are proposed to be stored in a no-SQL database instance.

CONTRIBUTING
------------
Participation by way of comments, (constructive) criticism or feature requests is most welcome, and shall be gratefully acknowledged.  If you are interested in submitting a patch, on the other hand, please also read the file _COPYRIGHT_ present together with this _README_.
