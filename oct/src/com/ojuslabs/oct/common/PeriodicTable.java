package com.ojuslabs.oct.common;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.ojuslabs.oct.exception.NotFoundException;

/**
 * PeriodicTable represents the chemical periodic table. It defines the
 * necessary constants for chemical elements, their atomic numbers, atomic
 * weights, isotopes and symbol string representations. It provides a set of
 * convenience methods to access and utilise the said data.
 */
public class PeriodicTable
{
    // Number of elements. This should be adjusted when adding or removing
    // elements from the list and the map.
    static int           _NUM_ELEMENTS = 327;

    static PeriodicTable _instance     = null; // Singleton assurance.

    public static PeriodicTable instance() {
        if (null != _instance) {
            return _instance;
        }

        _instance = new PeriodicTable();
        _instance.init();
        return _instance;
    }

    // Collections for ready reference.
    private ImmutableList<Element>        _elements;
    private ImmutableMap<String, Element> _symbolMap;

    PeriodicTable() {
        // Intentionally left blank.
    }

    /**
     * Initializes the sole instance with the tabular data of chemical elements.
     * <b>N.B.</b> The relative order of the elements in this list is of
     * significance. Do not change that.
     */
    void init() {
        // First the list of elements.
        ImmutableList.Builder<Element> lbuilder = ImmutableList.builder();
        lbuilder.add(new Element(0, "NONE", 0.0, -1));
        lbuilder.add(new Element(1, "H", 1.008, 1));
        lbuilder.add(new Element(2, "He", 4.003, 0));
        lbuilder.add(new Element(3, "Li", 6.941, 1));
        lbuilder.add(new Element(4, "Be", 9.012, 2));
        lbuilder.add(new Element(5, "B", 10.812, 3));
        lbuilder.add(new Element(6, "C", 12.011, 4));
        lbuilder.add(new Element(7, "N", 14.007, 3));
        lbuilder.add(new Element(8, "O", 15.999, 2));
        lbuilder.add(new Element(9, "F", 18.998, 1));
        lbuilder.add(new Element(10, "Ne", 20.18, 0));
        lbuilder.add(new Element(11, "Na", 22.99, 1));
        lbuilder.add(new Element(12, "Mg", 24.305, 2));
        lbuilder.add(new Element(13, "Al", 26.982, 6));
        lbuilder.add(new Element(14, "Si", 28.086, 4));
        lbuilder.add(new Element(15, "P", 30.974, 3));
        lbuilder.add(new Element(16, "S", 32.067, 2));
        lbuilder.add(new Element(17, "Cl", 35.453, 1));
        lbuilder.add(new Element(18, "Ar", 39.948, 0));
        lbuilder.add(new Element(19, "K", 39.098, 1));
        lbuilder.add(new Element(20, "Ca", 40.078, 2));
        lbuilder.add(new Element(21, "Sc", 44.956, -1));
        lbuilder.add(new Element(22, "Ti", 47.867, -1));
        lbuilder.add(new Element(23, "V", 50.942, -1));
        lbuilder.add(new Element(24, "Cr", 51.996, -1));
        lbuilder.add(new Element(25, "Mn", 54.938, -1));
        lbuilder.add(new Element(26, "Fe", 55.845, -1));
        lbuilder.add(new Element(27, "Co", 58.933, -1));
        lbuilder.add(new Element(28, "Ni", 58.693, -1));
        lbuilder.add(new Element(29, "Cu", 63.546, -1));
        lbuilder.add(new Element(30, "Zn", 65.39, -1));
        lbuilder.add(new Element(31, "Ga", 69.723, 3));
        lbuilder.add(new Element(32, "Ge", 72.61, 4));
        lbuilder.add(new Element(33, "As", 74.922, 3));
        lbuilder.add(new Element(34, "Se", 78.96, 2));
        lbuilder.add(new Element(35, "Br", 79.904, 1));
        lbuilder.add(new Element(36, "Kr", 83.8, 0));
        lbuilder.add(new Element(37, "Rb", 85.468, 1));
        lbuilder.add(new Element(38, "Sr", 87.62, 2));
        lbuilder.add(new Element(39, "Y", 88.906, -1));
        lbuilder.add(new Element(40, "Zr", 91.224, -1));
        lbuilder.add(new Element(41, "Nb", 92.906, -1));
        lbuilder.add(new Element(42, "Mo", 95.94, -1));
        lbuilder.add(new Element(43, "Tc", 98, -1));
        lbuilder.add(new Element(44, "Ru", 101.07, -1));
        lbuilder.add(new Element(45, "Rh", 102.906, -1));
        lbuilder.add(new Element(46, "Pd", 106.42, -1));
        lbuilder.add(new Element(47, "Ag", 107.868, -1));
        lbuilder.add(new Element(48, "Cd", 112.412, -1));
        lbuilder.add(new Element(49, "In", 114.818, 3));
        lbuilder.add(new Element(50, "Sn", 118.711, 4));
        lbuilder.add(new Element(51, "Sb", 121.76, 3));
        lbuilder.add(new Element(52, "Te", 127.6, 2));
        lbuilder.add(new Element(53, "I", 126.904, 1));
        lbuilder.add(new Element(54, "Xe", 131.29, 0));
        lbuilder.add(new Element(55, "Cs", 132.905, 1));
        lbuilder.add(new Element(56, "Ba", 137.328, 2));
        lbuilder.add(new Element(57, "La", 138.906, -1));
        lbuilder.add(new Element(58, "Ce", 140.116, -1));
        lbuilder.add(new Element(59, "Pr", 140.908, -1));
        lbuilder.add(new Element(60, "Nd", 144.24, -1));
        lbuilder.add(new Element(61, "Pm", 145, -1));
        lbuilder.add(new Element(62, "Sm", 150.36, -1));
        lbuilder.add(new Element(63, "Eu", 151.964, -1));
        lbuilder.add(new Element(64, "Gd", 157.25, -1));
        lbuilder.add(new Element(65, "Tb", 158.925, -1));
        lbuilder.add(new Element(66, "Dy", 162.5, -1));
        lbuilder.add(new Element(67, "Ho", 164.93, -1));
        lbuilder.add(new Element(68, "Er", 167.26, -1));
        lbuilder.add(new Element(69, "Tm", 168.934, -1));
        lbuilder.add(new Element(70, "Yb", 173.04, -1));
        lbuilder.add(new Element(71, "Lu", 174.967, -1));
        lbuilder.add(new Element(72, "Hf", 178.49, -1));
        lbuilder.add(new Element(73, "Ta", 180.948, -1));
        lbuilder.add(new Element(74, "W", 183.84, -1));
        lbuilder.add(new Element(75, "Re", 186.207, -1));
        lbuilder.add(new Element(76, "Os", 190.23, -1));
        lbuilder.add(new Element(77, "Ir", 192.217, -1));
        lbuilder.add(new Element(78, "Pt", 195.078, -1));
        lbuilder.add(new Element(79, "Au", 196.967, -1));
        lbuilder.add(new Element(80, "Hg", 200.59, -1));
        lbuilder.add(new Element(81, "Tl", 204.383, 3));
        lbuilder.add(new Element(82, "Pb", 207.2, 4));
        lbuilder.add(new Element(83, "Bi", 208.98, 3));
        lbuilder.add(new Element(84, "Po", 209, 2));
        lbuilder.add(new Element(85, "At", 210, 1));
        lbuilder.add(new Element(86, "Rn", 222, 0));
        lbuilder.add(new Element(87, "Fr", 223, 1));
        lbuilder.add(new Element(88, "Ra", 226, 2));
        lbuilder.add(new Element(89, "Ac", 227, -1));
        lbuilder.add(new Element(90, "Th", 232.038, -1));
        lbuilder.add(new Element(91, "Pa", 231.036, -1));
        lbuilder.add(new Element(92, "U", 238.029, -1));
        lbuilder.add(new Element(93, "Np", 237, -1));
        lbuilder.add(new Element(94, "Pu", 244, -1));
        lbuilder.add(new Element(95, "Am", 243, -1));
        lbuilder.add(new Element(96, "Cm", 247, -1));
        lbuilder.add(new Element(97, "Bk", 247, -1));
        lbuilder.add(new Element(98, "Cf", 251, -1));
        lbuilder.add(new Element(99, "Es", 252, -1));
        lbuilder.add(new Element(100, "Fm", 257, -1));
        lbuilder.add(new Element(101, "Md", 258, -1));
        lbuilder.add(new Element(102, "No", 259, -1));
        lbuilder.add(new Element(103, "Lr", 262, -1));
        lbuilder.add(new Element(104, "Rf", 267, -1));
        lbuilder.add(new Element(105, "Db", 268, -1));
        lbuilder.add(new Element(106, "Sg", 269, -1));
        lbuilder.add(new Element(107, "Bh", 270, -1));
        lbuilder.add(new Element(108, "Hs", 269, -1));
        lbuilder.add(new Element(109, "Mt", 278, -1));
        lbuilder.add(new Element(110, "Ds", 281, -1));
        lbuilder.add(new Element(111, "Rg", 281, -1));
        lbuilder.add(new Element(112, "Cn", 285, -1));
        lbuilder.add(new Element(1, "D", 2.014101778, 1));
        lbuilder.add(new Element(1, "H_2", 2.014101778, 1));
        lbuilder.add(new Element(1, "T", 3.016049278, 1));
        lbuilder.add(new Element(1, "H_3", 3.016049278, 1));
        lbuilder.add(new Element(1, "H_4", 4.02781, 1));
        lbuilder.add(new Element(1, "H_5", 5.03531, 1));
        lbuilder.add(new Element(1, "H_6", 6.04494, 1));
        lbuilder.add(new Element(1, "H_7", 7.05275, 1));
        lbuilder.add(new Element(6, "C_8", 8.037675, 4));
        lbuilder.add(new Element(6, "C_9", 9.0310367, 4));
        lbuilder.add(new Element(6, "C_10", 10.0168532, 4));
        lbuilder.add(new Element(6, "C_11", 11.0114336, 4));
        lbuilder.add(new Element(6, "C_13", 13.00335484, 4));
        lbuilder.add(new Element(6, "C_14", 14.00324199, 4));
        lbuilder.add(new Element(6, "C_15", 15.0105993, 4));
        lbuilder.add(new Element(6, "C_16", 16.014701, 4));
        lbuilder.add(new Element(6, "C_17", 17.022586, 4));
        lbuilder.add(new Element(6, "C_18", 18.02676, 4));
        lbuilder.add(new Element(6, "C_19", 19.03481, 4));
        lbuilder.add(new Element(6, "C_20", 20.04032, 4));
        lbuilder.add(new Element(6, "C_21", 21.04934, 4));
        lbuilder.add(new Element(6, "C_22", 22.0572, 4));
        lbuilder.add(new Element(7, "N_10", 10.04165, 3));
        lbuilder.add(new Element(7, "N_11", 11.02609, 3));
        lbuilder.add(new Element(7, "N_12", 12.0186132, 3));
        lbuilder.add(new Element(7, "N_13", 13.00573861, 3));
        lbuilder.add(new Element(7, "N_15", 15.0001089, 3));
        lbuilder.add(new Element(7, "N_16", 16.0061017, 3));
        lbuilder.add(new Element(7, "N_17", 17.00845, 3));
        lbuilder.add(new Element(7, "N_18", 18.014079, 3));
        lbuilder.add(new Element(7, "N_19", 19.017029, 3));
        lbuilder.add(new Element(7, "N_20", 20.02337, 3));
        lbuilder.add(new Element(7, "N_21", 21.02711, 3));
        lbuilder.add(new Element(7, "N_22", 22.03439, 3));
        lbuilder.add(new Element(7, "N_23", 23.04122, 3));
        lbuilder.add(new Element(7, "N_24", 24.05104, 3));
        lbuilder.add(new Element(7, "N_25", 25.06066, 3));
        lbuilder.add(new Element(8, "O_12", 12.034405, 2));
        lbuilder.add(new Element(8, "O_13", 13.024812, 2));
        lbuilder.add(new Element(8, "O_14", 14.00859625, 2));
        lbuilder.add(new Element(8, "O_15", 15.0030656, 2));
        lbuilder.add(new Element(8, "O_17", 16.9991317, 2));
        lbuilder.add(new Element(8, "O_18", 17.999161, 2));
        lbuilder.add(new Element(8, "O_19", 19.00358, 2));
        lbuilder.add(new Element(8, "O_20", 20.0040767, 2));
        lbuilder.add(new Element(8, "O_21", 21.008656, 2));
        lbuilder.add(new Element(8, "O_22", 22.00997, 2));
        lbuilder.add(new Element(8, "O_23", 23.01569, 2));
        lbuilder.add(new Element(8, "O_24", 24.02047, 2));
        lbuilder.add(new Element(8, "O_25", 25.02946, 2));
        lbuilder.add(new Element(8, "O_26", 26.03834, 2));
        lbuilder.add(new Element(8, "O_27", 27.04826, 2));
        lbuilder.add(new Element(8, "O_28", 28.05781, 2));
        lbuilder.add(new Element(9, "F_14", 14.03506, 1));
        lbuilder.add(new Element(9, "F_15", 15.01801, 1));
        lbuilder.add(new Element(9, "F_16", 16.011466, 1));
        lbuilder.add(new Element(9, "F_17", 17.00209524, 1));
        lbuilder.add(new Element(9, "F_18", 18.000938, 1));
        lbuilder.add(new Element(9, "F_20", 19.99998132, 1));
        lbuilder.add(new Element(9, "F_21", 20.999949, 1));
        lbuilder.add(new Element(9, "F_22", 22.002999, 1));
        lbuilder.add(new Element(9, "F_23", 23.00357, 1));
        lbuilder.add(new Element(9, "F_24", 24.00812, 1));
        lbuilder.add(new Element(9, "F_25", 25.0121, 1));
        lbuilder.add(new Element(9, "F_26", 26.01962, 1));
        lbuilder.add(new Element(9, "F_27", 27.02676, 1));
        lbuilder.add(new Element(9, "F_28", 28.03567, 1));
        lbuilder.add(new Element(9, "F_29", 29.04326, 1));
        lbuilder.add(new Element(9, "F_30", 30.0525, 1));
        lbuilder.add(new Element(9, "F_31", 31.06043, 1));
        lbuilder.add(new Element(15, "P_24", 24.03435, 3));
        lbuilder.add(new Element(15, "P_25", 25.02026, 3));
        lbuilder.add(new Element(15, "P_26", 26.01178, 3));
        lbuilder.add(new Element(15, "P_27", 26.99923, 3));
        lbuilder.add(new Element(15, "P_28", 27.992315, 3));
        lbuilder.add(new Element(15, "P_29", 28.9818006, 3));
        lbuilder.add(new Element(15, "P_30", 29.9783138, 3));
        lbuilder.add(new Element(15, "P_32", 31.97390727, 3));
        lbuilder.add(new Element(15, "P_33", 32.9717255, 3));
        lbuilder.add(new Element(15, "P_34", 33.973636, 3));
        lbuilder.add(new Element(15, "P_35", 34.9733141, 3));
        lbuilder.add(new Element(15, "P_36", 35.97826, 3));
        lbuilder.add(new Element(15, "P_37", 36.97961, 3));
        lbuilder.add(new Element(15, "P_38", 37.98416, 3));
        lbuilder.add(new Element(15, "P_39", 38.98618, 3));
        lbuilder.add(new Element(15, "P_40", 39.9913, 3));
        lbuilder.add(new Element(15, "P_41", 40.99434, 3));
        lbuilder.add(new Element(15, "P_42", 42.00101, 3));
        lbuilder.add(new Element(15, "P_43", 43.00619, 3));
        lbuilder.add(new Element(15, "P_44", 44.01299, 3));
        lbuilder.add(new Element(15, "P_45", 45.01922, 3));
        lbuilder.add(new Element(15, "P_46", 46.02738, 3));
        lbuilder.add(new Element(16, "S_26", 26.02788, 2));
        lbuilder.add(new Element(16, "S_27", 27.01883, 2));
        lbuilder.add(new Element(16, "S_28", 28.00437, 2));
        lbuilder.add(new Element(16, "S_29", 28.99661, 2));
        lbuilder.add(new Element(16, "S_30", 29.984903, 2));
        lbuilder.add(new Element(16, "S_31", 30.9795547, 2));
        lbuilder.add(new Element(16, "S_33", 32.97145876, 2));
        lbuilder.add(new Element(16, "S_34", 33.9678669, 2));
        lbuilder.add(new Element(16, "S_35", 34.96903216, 2));
        lbuilder.add(new Element(16, "S_36", 35.96708076, 2));
        lbuilder.add(new Element(16, "S_37", 36.97112557, 2));
        lbuilder.add(new Element(16, "S_38", 37.971163, 2));
        lbuilder.add(new Element(16, "S_39", 38.97513, 2));
        lbuilder.add(new Element(16, "S_40", 39.97545, 2));
        lbuilder.add(new Element(16, "S_41", 40.97958, 2));
        lbuilder.add(new Element(16, "S_42", 41.98102, 2));
        lbuilder.add(new Element(16, "S_43", 42.98715, 2));
        lbuilder.add(new Element(16, "S_44", 43.99021, 2));
        lbuilder.add(new Element(16, "S_45", 44.99651, 2));
        lbuilder.add(new Element(16, "S_46", 46.00075, 2));
        lbuilder.add(new Element(16, "S_47", 47.00859, 2));
        lbuilder.add(new Element(16, "S_48", 48.01417, 2));
        lbuilder.add(new Element(16, "S_49", 49.02362, 2));
        lbuilder.add(new Element(17, "Cl_28", 28.02851, 1));
        lbuilder.add(new Element(17, "Cl_29", 29.01411, 1));
        lbuilder.add(new Element(17, "Cl_30", 30.00477, 1));
        lbuilder.add(new Element(17, "Cl_31", 30.99241, 1));
        lbuilder.add(new Element(17, "Cl_32", 31.98569, 1));
        lbuilder.add(new Element(17, "Cl_33", 32.9774519, 1));
        lbuilder.add(new Element(17, "Cl_34", 33.97376282, 1));
        lbuilder.add(new Element(17, "Cl_36", 35.96830698, 1));
        lbuilder.add(new Element(17, "Cl_37", 36.96590259, 1));
        lbuilder.add(new Element(17, "Cl_38", 37.96801043, 1));
        lbuilder.add(new Element(17, "Cl_39", 38.9680082, 1));
        lbuilder.add(new Element(17, "Cl_40", 39.97042, 1));
        lbuilder.add(new Element(17, "Cl_41", 40.97068, 1));
        lbuilder.add(new Element(17, "Cl_42", 41.97325, 1));
        lbuilder.add(new Element(17, "Cl_43", 42.97405, 1));
        lbuilder.add(new Element(17, "Cl_44", 43.97828, 1));
        lbuilder.add(new Element(17, "Cl_45", 44.98029, 1));
        lbuilder.add(new Element(17, "Cl_46", 45.98421, 1));
        lbuilder.add(new Element(17, "Cl_47", 46.98871, 1));
        lbuilder.add(new Element(17, "Cl_48", 47.99495, 1));
        lbuilder.add(new Element(17, "Cl_49", 49.00032, 1));
        lbuilder.add(new Element(17, "Cl_50", 50.00784, 1));
        lbuilder.add(new Element(17, "Cl_51", 51.01449, 1));
        lbuilder.add(new Element(35, "Br_67", 66.96479, 1));
        lbuilder.add(new Element(35, "Br_68", 67.95852, 1));
        lbuilder.add(new Element(35, "Br_69", 68.95011, 1));
        lbuilder.add(new Element(35, "Br_70", 69.94479, 1));
        lbuilder.add(new Element(35, "Br_71", 70.93874, 1));
        lbuilder.add(new Element(35, "Br_72", 71.93664, 1));
        lbuilder.add(new Element(35, "Br_73", 72.93169, 1));
        lbuilder.add(new Element(35, "Br_74", 73.929891, 1));
        lbuilder.add(new Element(35, "Br_75", 74.925776, 1));
        lbuilder.add(new Element(35, "Br_76", 75.924541, 1));
        lbuilder.add(new Element(35, "Br_77", 76.921379, 1));
        lbuilder.add(new Element(35, "Br_78", 77.921146, 1));
        lbuilder.add(new Element(35, "Br_79", 78.9183371, 1));
        lbuilder.add(new Element(35, "Br_81", 80.9162906, 1));
        lbuilder.add(new Element(35, "Br_82", 81.9168041, 1));
        lbuilder.add(new Element(35, "Br_83", 82.91518, 1));
        lbuilder.add(new Element(35, "Br_84", 83.916479, 1));
        lbuilder.add(new Element(35, "Br_85", 84.915608, 1));
        lbuilder.add(new Element(35, "Br_86", 85.918798, 1));
        lbuilder.add(new Element(35, "Br_87", 86.920711, 1));
        lbuilder.add(new Element(35, "Br_88", 87.92407, 1));
        lbuilder.add(new Element(35, "Br_89", 88.92639, 1));
        lbuilder.add(new Element(35, "Br_90", 89.93063, 1));
        lbuilder.add(new Element(35, "Br_91", 90.93397, 1));
        lbuilder.add(new Element(35, "Br_92", 91.93926, 1));
        lbuilder.add(new Element(35, "Br_93", 92.94305, 1));
        lbuilder.add(new Element(35, "Br_94", 93.94868, 1));
        lbuilder.add(new Element(35, "Br_95", 94.95287, 1));
        lbuilder.add(new Element(35, "Br_96", 95.95853, 1));
        lbuilder.add(new Element(35, "Br_97", 96.9628, 1));
        lbuilder.add(new Element(53, "I_108", 107.94348, 1));
        lbuilder.add(new Element(53, "I_109", 108.93815, 1));
        lbuilder.add(new Element(53, "I_110", 109.93524, 1));
        lbuilder.add(new Element(53, "I_111", 110.93028, 1));
        lbuilder.add(new Element(53, "I_112", 111.92797, 1));
        lbuilder.add(new Element(53, "I_113", 112.92364, 1));
        lbuilder.add(new Element(53, "I_114", 113.92185, 1));
        lbuilder.add(new Element(53, "I_115", 114.91805, 1));
        lbuilder.add(new Element(53, "I_116", 115.91681, 1));
        lbuilder.add(new Element(53, "I_117", 116.91365, 1));
        lbuilder.add(new Element(53, "I_118", 117.913074, 1));
        lbuilder.add(new Element(53, "I_119", 118.91007, 1));
        lbuilder.add(new Element(53, "I_120", 119.910048, 1));
        lbuilder.add(new Element(53, "I_121", 120.907367, 1));
        lbuilder.add(new Element(53, "I_122", 121.907589, 1));
        lbuilder.add(new Element(53, "I_123", 122.905589, 1));
        lbuilder.add(new Element(53, "I_124", 123.9062099, 1));
        lbuilder.add(new Element(53, "I_125", 124.9046302, 1));
        lbuilder.add(new Element(53, "I_126", 125.905624, 1));
        lbuilder.add(new Element(53, "I_128", 127.905809, 1));
        lbuilder.add(new Element(53, "I_129", 128.904988, 1));
        lbuilder.add(new Element(53, "I_130", 129.906674, 1));
        lbuilder.add(new Element(53, "I_131", 130.9061246, 1));
        lbuilder.add(new Element(53, "I_132", 131.907997, 1));
        lbuilder.add(new Element(53, "I_133", 132.907797, 1));
        lbuilder.add(new Element(53, "I_134", 133.909744, 1));
        lbuilder.add(new Element(53, "I_135", 134.910048, 1));
        lbuilder.add(new Element(53, "I_136", 135.91465, 1));
        lbuilder.add(new Element(53, "I_137", 136.917871, 1));
        lbuilder.add(new Element(53, "I_138", 137.92235, 1));
        lbuilder.add(new Element(53, "I_139", 138.9261, 1));
        lbuilder.add(new Element(53, "I_140", 139.931, 1));
        lbuilder.add(new Element(53, "I_141", 140.93503, 1));
        lbuilder.add(new Element(53, "I_142", 141.94018, 1));
        lbuilder.add(new Element(53, "I_143", 142.94456, 1));
        lbuilder.add(new Element(53, "I_144", 143.94999, 1));
        lbuilder.add(new Element(0, "Q_STAR", 0, -1));
        lbuilder.add(new Element(0, "Q_a", 0, -1));
        lbuilder.add(new Element(0, "Q_A", 0, -1));
        lbuilder.add(new Element(0, "Q_AH", 0, -1));
        lbuilder.add(new Element(0, "Q_M", 0, -1));
        lbuilder.add(new Element(0, "Q_MH", 0, -1));
        lbuilder.add(new Element(0, "Q_Q", 0, -1));
        lbuilder.add(new Element(0, "Q_QH", 0, -1));
        lbuilder.add(new Element(0, "Q_X", 0, -1));
        lbuilder.add(new Element(0, "Q_XH", 0, -1));
        _elements = lbuilder.build();

        // Now, we initialise the map.
        ImmutableMap.Builder<String, Element> hbuilder = ImmutableMap.builder();
        for (Element elem : _elements) {
            hbuilder.put(elem.symbol, elem);
        }
        _symbolMap = hbuilder.build();
    }

    /**
     * @param sym
     *            Element's symbol.
     * @return Requested element.
     * @throws NotFoundException
     */
    public Element element(String sym) throws NotFoundException {
        Element el = _symbolMap.get(sym);
        if (null == el) {
            throw new NotFoundException("Invalid symbol given: " + sym);
        }
        return el;
    }

    /**
     * @param num
     *            Atomic number.
     * @return Requested element.
     * @throws NotFoundException
     */
    public Element element(int num) throws NotFoundException {
        if ((num < 1) || (num >= _NUM_ELEMENTS)) {
            throw new NotFoundException(String.format(
                    "Invalid atomic number given: %s", num));
        }
        return _elements.get(num);
    }

    /**
     * @param sym
     *            Element's symbol.
     * @param iso
     *            Isotope mass; must be the nearest integer.
     * @return Requested element isotope.
     * @throws NotFoundException
     */
    public Element isotope(String sym, int iso) throws NotFoundException {
        return element(String.format("%s_%d", sym, iso));
    }

    /**
     * @param num
     *            Element's atomic number.
     * @param iso
     *            Isotope mass; must be the nearest integer.
     * @return Requested element.
     * @throws NotFoundException
     */
    public Element isotope(int num, int iso) throws NotFoundException {
        String sym = element(num).symbol;
        return element(String.format("%s_%d", sym, iso));
    }
}
