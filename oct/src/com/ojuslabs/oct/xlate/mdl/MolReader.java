/**
 * Copyright (c) 2012-2013 Ojus Software Labs Private Limited.
 * 
 * All rights reserved. Please see the files README.md, LICENSE and COPYRIGHT
 * for details.
 */

package com.ojuslabs.oct.xlate.mdl;

import java.util.List;

import com.ojuslabs.oct.data.Molecule;

/**
 * MolReader is the interface that is implemented by all MDL-format `.mol' text
 * readers.
 */
public interface MolReader
{
    /**
     * Parses the text of the molecule supplied as a string. It parses the
     * connection table, properties and tags in the text, constructing a
     * molecule instance along the way.
     * 
     * The flags <code>skipCtab</code>, <code>skipProps</code> and
     * <code>skipTags</code> can be used to specify that the corresponding
     * section should be skipped during parsing. These can be utilised when the
     * connection table, properties or tags (or any combination of the above)
     * are not required for downstream processing. It can result in an increase
     * in performance. <b>N.B.</b> The header part is parsed <b>always</b>.
     * 
     * @param l
     *            List of lines of input molecule's text.
     * @param skipCtab
     *            If true, CTAB section is not parsed.
     * @param skipProps
     *            If true, properties ("M  XXX") are not parsed.
     * @param skipTags
     *            If true, extended attribute tags ("&gt; &lt;XXX&gt;") are not
     *            parsed.
     * @return Molecule object constructed as a result of this parsing.
     */
    Molecule parse(List<String> l, boolean skipCtab, boolean skipProps,
            boolean skipTags);
}
