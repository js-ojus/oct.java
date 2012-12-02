package com.ojuslabs.oct.xlate.mdl;

import java.util.List;

import com.ojuslabs.oct.data.Molecule;

/**
 * MolParserHook defines a single-method interface that MolParser callbacks have
 * to implement.
 */
public interface MolParserHook
{
    /**
     * Processes the molecule text and the molecule object as per the hook's
     * requirements. <b>N.B.</b> This method should not throw any exceptions.
     * 
     * @param l
     *            Part or full text of the molecule, as may be applicable.
     * @param m
     *            Molecule object; guaranteed to be non-<code>null</code>.
     */
    void apply(List<String> l, Molecule m);
}
