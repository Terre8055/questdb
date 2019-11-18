/*******************************************************************************
 *    ___                  _   ____  ____
 *   / _ \ _   _  ___  ___| |_|  _ \| __ )
 *  | | | | | | |/ _ \/ __| __| | | |  _ \
 *  | |_| | |_| |  __/\__ \ |_| |_| | |_) |
 *   \__\_\\__,_|\___||___/\__|____/|____/
 *
 * Copyright (C) 2014-2019 Appsicle
 *
 * This program is free software: you can redistribute it and/or  modify
 * it under the terms of the GNU Affero General Public License, version 3,
 * as published by the Free Software Foundation.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 ******************************************************************************/

package io.questdb.std;

import io.questdb.std.str.CharSink;

import java.util.Arrays;

public final class Numbers {

    public static final int INT_NaN = Integer.MIN_VALUE;
    public static final long LONG_NaN = Long.MIN_VALUE;
    public static final double TOLERANCE = 1E-10d;
    public static final int SIZE_1MB = 1024 * 1024;
    public static final char[] hexDigits = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
    public final static int[] hexNumbers;
    private static final long[] pow10;
    private static final long LONG_OVERFLOW_MAX = Long.MAX_VALUE / 10 + 1;
    private static final long INT_OVERFLOW_MAX = Integer.MAX_VALUE / 10;
    private final static String NaN = "NaN";
    private static final String INFINITY = "Infinity";
    private static final double[] pow10d = new double[]{1, 1E1, 1E2, 1E3, 1E4, 1E5, 1E6, 1E7, 1E8, 1E9, 1E10, 1E11, 1E12, 1E13, 1E14, 1E15, 1E16, 1E17, 1E18, 1E19, 1E20, 1E21, 1E22, 1E23, 1E24, 1E25, 1E26, 1E27, 1E28, 1E29, 1E30, 1E31, 1E32, 1E33, 1E34, 1E35, 1E36, 1E37, 1E38, 1E39, 1E40, 1E41, 1E42, 1E43, 1E44, 1E45, 1E46, 1E47, 1E48, 1E49, 1E50, 1E51, 1E52, 1E53, 1E54, 1E55, 1E56, 1E57, 1E58, 1E59, 1E60, 1E61, 1E62, 1E63, 1E64, 1E65, 1E66, 1E67, 1E68, 1E69, 1E70, 1E71, 1E72, 1E73, 1E74, 1E75, 1E76, 1E77, 1E78, 1E79, 1E80, 1E81, 1E82, 1E83, 1E84, 1E85, 1E86, 1E87, 1E88, 1E89, 1E90, 1E91, 1E92, 1E93, 1E94, 1E95, 1E96, 1E97, 1E98, 1E99, 1E100, 1E101, 1E102, 1E103, 1E104, 1E105, 1E106, 1E107, 1E108, 1E109, 1E110, 1E111, 1E112, 1E113, 1E114, 1E115, 1E116, 1E117, 1E118, 1E119, 1E120, 1E121, 1E122, 1E123, 1E124, 1E125, 1E126, 1E127, 1E128, 1E129, 1E130, 1E131, 1E132, 1E133, 1E134, 1E135, 1E136, 1E137, 1E138, 1E139, 1E140, 1E141, 1E142, 1E143, 1E144, 1E145, 1E146, 1E147, 1E148, 1E149, 1E150, 1E151, 1E152, 1E153, 1E154, 1E155, 1E156, 1E157, 1E158, 1E159, 1E160, 1E161, 1E162, 1E163, 1E164, 1E165, 1E166, 1E167, 1E168, 1E169, 1E170, 1E171, 1E172, 1E173, 1E174, 1E175, 1E176, 1E177, 1E178, 1E179, 1E180, 1E181, 1E182, 1E183, 1E184, 1E185, 1E186, 1E187, 1E188, 1E189, 1E190, 1E191, 1E192, 1E193, 1E194, 1E195, 1E196, 1E197, 1E198, 1E199, 1E200, 1E201, 1E202, 1E203, 1E204, 1E205, 1E206, 1E207, 1E208, 1E209, 1E210, 1E211, 1E212, 1E213, 1E214, 1E215, 1E216, 1E217, 1E218, 1E219, 1E220, 1E221, 1E222, 1E223, 1E224, 1E225, 1E226, 1E227, 1E228, 1E229, 1E230, 1E231, 1E232, 1E233, 1E234, 1E235, 1E236, 1E237, 1E238, 1E239, 1E240, 1E241, 1E242, 1E243, 1E244, 1E245, 1E246, 1E247, 1E248, 1E249, 1E250, 1E251, 1E252, 1E253, 1E254, 1E255, 1E256, 1E257, 1E258, 1E259, 1E260, 1E261, 1E262, 1E263, 1E264, 1E265, 1E266, 1E267, 1E268, 1E269, 1E270, 1E271, 1E272, 1E273, 1E274, 1E275, 1E276, 1E277, 1E278, 1E279, 1E280, 1E281, 1E282, 1E283, 1E284, 1E285, 1E286, 1E287, 1E288, 1E289, 1E290, 1E291, 1E292, 1E293, 1E294, 1E295, 1E296, 1E297, 1E298, 1E299, 1E300, 1E301, 1E302, 1E303, 1E304, 1E305, 1E306, 1E307, 1E308};
    private static final double[] pow10dNeg =
            new double[]{1, 1E-1, 1E-2, 1E-3, 1E-4, 1E-5, 1E-6, 1E-7, 1E-8, 1E-9, 1E-10, 1E-11, 1E-12, 1E-13, 1E-14, 1E-15, 1E-16, 1E-17, 1E-18, 1E-19, 1E-20, 1E-21, 1E-22, 1E-23, 1E-24, 1E-25, 1E-26, 1E-27, 1E-28, 1E-29, 1E-30, 1E-31, 1E-32, 1E-33, 1E-34, 1E-35, 1E-36, 1E-37, 1E-38, 1E-39, 1E-40, 1E-41, 1E-42, 1E-43, 1E-44, 1E-45, 1E-46, 1E-47, 1E-48, 1E-49, 1E-50, 1E-51, 1E-52, 1E-53, 1E-54, 1E-55, 1E-56, 1E-57, 1E-58, 1E-59, 1E-60, 1E-61, 1E-62, 1E-63, 1E-64, 1E-65, 1E-66, 1E-67, 1E-68, 1E-69, 1E-70, 1E-71, 1E-72, 1E-73, 1E-74, 1E-75, 1E-76, 1E-77, 1E-78, 1E-79, 1E-80, 1E-81, 1E-82, 1E-83, 1E-84, 1E-85, 1E-86, 1E-87, 1E-88, 1E-89, 1E-90, 1E-91, 1E-92, 1E-93, 1E-94, 1E-95, 1E-96, 1E-97, 1E-98, 1E-99, 1E-100, 1E-101, 1E-102, 1E-103, 1E-104, 1E-105, 1E-106, 1E-107, 1E-108, 1E-109, 1E-110, 1E-111, 1E-112, 1E-113, 1E-114, 1E-115, 1E-116, 1E-117, 1E-118, 1E-119, 1E-120, 1E-121, 1E-122, 1E-123, 1E-124, 1E-125, 1E-126, 1E-127, 1E-128, 1E-129, 1E-130, 1E-131, 1E-132, 1E-133, 1E-134, 1E-135, 1E-136, 1E-137, 1E-138, 1E-139, 1E-140, 1E-141, 1E-142, 1E-143, 1E-144, 1E-145, 1E-146, 1E-147, 1E-148, 1E-149, 1E-150, 1E-151, 1E-152, 1E-153, 1E-154, 1E-155, 1E-156, 1E-157, 1E-158, 1E-159, 1E-160, 1E-161, 1E-162, 1E-163, 1E-164, 1E-165, 1E-166, 1E-167, 1E-168, 1E-169, 1E-170, 1E-171, 1E-172, 1E-173, 1E-174, 1E-175, 1E-176, 1E-177, 1E-178, 1E-179, 1E-180, 1E-181, 1E-182, 1E-183, 1E-184, 1E-185, 1E-186, 1E-187, 1E-188, 1E-189, 1E-190, 1E-191, 1E-192, 1E-193, 1E-194, 1E-195, 1E-196, 1E-197, 1E-198, 1E-199, 1E-200, 1E-201, 1E-202, 1E-203, 1E-204, 1E-205, 1E-206, 1E-207, 1E-208, 1E-209, 1E-210, 1E-211, 1E-212, 1E-213, 1E-214, 1E-215, 1E-216, 1E-217, 1E-218, 1E-219, 1E-220, 1E-221, 1E-222, 1E-223, 1E-224, 1E-225, 1E-226, 1E-227, 1E-228, 1E-229, 1E-230, 1E-231, 1E-232, 1E-233, 1E-234, 1E-235, 1E-236, 1E-237, 1E-238, 1E-239, 1E-240, 1E-241, 1E-242, 1E-243, 1E-244, 1E-245, 1E-246, 1E-247, 1E-248, 1E-249, 1E-250, 1E-251, 1E-252, 1E-253, 1E-254, 1E-255, 1E-256, 1E-257, 1E-258, 1E-259, 1E-260, 1E-261, 1E-262, 1E-263, 1E-264, 1E-265, 1E-266, 1E-267, 1E-268, 1E-269, 1E-270, 1E-271, 1E-272, 1E-273, 1E-274, 1E-275, 1E-276, 1E-277, 1E-278, 1E-279, 1E-280, 1E-281, 1E-282, 1E-283, 1E-284, 1E-285, 1E-286, 1E-287, 1E-288, 1E-289, 1E-290, 1E-291, 1E-292, 1E-293, 1E-294, 1E-295, 1E-296, 1E-297, 1E-298, 1E-299, 1E-300, 1E-301, 1E-302, 1E-303, 1E-304, 1E-305, 1E-306, 1E-307, 1E-308};
    private static final float[] pow10f = new float[]{1, 1E1f, 1E2f, 1E3f, 1E4f, 1E5f, 1E6f, 1E7f, 1E8f, 1E9f, 1E10f, 1E11f, 1E12f, 1E13f, 1E14f, 1E15f, 1E16f, 1E17f, 1E18f, 1E19f, 1E20f, 1E21f, 1E22f, 1E23f, 1E24f, 1E25f, 1E26f, 1E27f, 1E28f, 1E29f, 1E30f, 1E31f, 1E32f, 1E33f, 1E34f, 1E35f, 1E36f, 1E37f, 1E38f};
    private final static int pow10max;
    private static final LongHexAppender[] longHexAppender = new LongHexAppender[Long.SIZE + 1];
    private static final LongHexAppender[] longHexAppenderPad64 = new LongHexAppender[Long.SIZE + 1];

    static {
        pow10 = new long[20];
        pow10max = 14;
        pow10[0] = 1;
        for (int i = 1; i < pow10.length; i++) {
            pow10[i] = pow10[i - 1] * 10;
        }

        hexNumbers = new int[128];
        Arrays.fill(hexNumbers, -1);
        hexNumbers['0'] = 0;
        hexNumbers['1'] = 1;
        hexNumbers['2'] = 2;
        hexNumbers['3'] = 3;
        hexNumbers['4'] = 4;
        hexNumbers['5'] = 5;
        hexNumbers['6'] = 6;
        hexNumbers['7'] = 7;
        hexNumbers['8'] = 8;
        hexNumbers['9'] = 9;
        hexNumbers['A'] = 10;
        hexNumbers['a'] = 10;
        hexNumbers['B'] = 11;
        hexNumbers['b'] = 11;
        hexNumbers['C'] = 12;
        hexNumbers['c'] = 12;
        hexNumbers['D'] = 13;
        hexNumbers['d'] = 13;
        hexNumbers['E'] = 14;
        hexNumbers['e'] = 14;
        hexNumbers['F'] = 15;
        hexNumbers['f'] = 15;
    }

    static {
        final LongHexAppender a4 = Numbers::appendLongHex4;
        longHexAppender[0] = a4;
        longHexAppender[1] = a4;
        longHexAppender[2] = a4;
        longHexAppender[3] = a4;
        longHexAppender[4] = a4;

        final LongHexAppender a8 = Numbers::appendLongHex8;
        longHexAppender[5] = a8;
        longHexAppender[6] = a8;
        longHexAppender[7] = a8;
        longHexAppender[8] = a8;

        LongHexAppender a12 = Numbers::appendLongHex12;
        longHexAppender[9] = a12;
        longHexAppender[10] = a12;
        longHexAppender[11] = a12;
        longHexAppender[12] = a12;

        LongHexAppender a16 = Numbers::appendLongHex16;
        longHexAppender[13] = a16;
        longHexAppender[14] = a16;
        longHexAppender[15] = a16;
        longHexAppender[16] = a16;

        LongHexAppender a20 = Numbers::appendLongHex20;
        longHexAppender[17] = a20;
        longHexAppender[18] = a20;
        longHexAppender[19] = a20;
        longHexAppender[20] = a20;

        LongHexAppender a24 = Numbers::appendLongHex24;
        longHexAppender[21] = a24;
        longHexAppender[22] = a24;
        longHexAppender[23] = a24;
        longHexAppender[24] = a24;

        LongHexAppender a28 = Numbers::appendLongHex28;
        longHexAppender[25] = a28;
        longHexAppender[26] = a28;
        longHexAppender[27] = a28;
        longHexAppender[28] = a28;

        LongHexAppender a32 = Numbers::appendLongHex32;
        longHexAppender[29] = a32;
        longHexAppender[30] = a32;
        longHexAppender[31] = a32;
        longHexAppender[32] = a32;

        LongHexAppender a36 = Numbers::appendLongHex36;
        longHexAppender[33] = a36;
        longHexAppender[34] = a36;
        longHexAppender[35] = a36;
        longHexAppender[36] = a36;

        LongHexAppender a40 = Numbers::appendLongHex40;
        longHexAppender[37] = a40;
        longHexAppender[38] = a40;
        longHexAppender[39] = a40;
        longHexAppender[40] = a40;

        LongHexAppender a44 = Numbers::appendLongHex44;
        longHexAppender[41] = a44;
        longHexAppender[42] = a44;
        longHexAppender[43] = a44;
        longHexAppender[44] = a44;

        LongHexAppender a48 = Numbers::appendLongHex48;
        longHexAppender[45] = a48;
        longHexAppender[46] = a48;
        longHexAppender[47] = a48;
        longHexAppender[48] = a48;

        LongHexAppender a52 = Numbers::appendLongHex52;
        longHexAppender[49] = a52;
        longHexAppender[50] = a52;
        longHexAppender[51] = a52;
        longHexAppender[52] = a52;

        LongHexAppender a56 = Numbers::appendLongHex56;
        longHexAppender[53] = a56;
        longHexAppender[54] = a56;
        longHexAppender[55] = a56;
        longHexAppender[56] = a56;

        LongHexAppender a60 = Numbers::appendLongHex60;
        longHexAppender[57] = a60;
        longHexAppender[58] = a60;
        longHexAppender[59] = a60;
        longHexAppender[60] = a60;

        LongHexAppender a64 = Numbers::appendLongHex64;
        longHexAppender[61] = a64;
        longHexAppender[62] = a64;
        longHexAppender[63] = a64;
        longHexAppender[64] = a64;
    }

    static {
        final LongHexAppender a4 = Numbers::appendLongHex4Pad64;
        longHexAppenderPad64[0] = a4;
        longHexAppenderPad64[1] = a4;
        longHexAppenderPad64[2] = a4;
        longHexAppenderPad64[3] = a4;
        longHexAppenderPad64[4] = a4;

        final LongHexAppender a8 = Numbers::appendLongHex8Pad64;
        longHexAppenderPad64[5] = a8;
        longHexAppenderPad64[6] = a8;
        longHexAppenderPad64[7] = a8;
        longHexAppenderPad64[8] = a8;

        LongHexAppender a12 = Numbers::appendLongHex12Pad64;
        longHexAppenderPad64[9] = a12;
        longHexAppenderPad64[10] = a12;
        longHexAppenderPad64[11] = a12;
        longHexAppenderPad64[12] = a12;

        LongHexAppender a16 = Numbers::appendLongHex16Pad64;
        longHexAppenderPad64[13] = a16;
        longHexAppenderPad64[14] = a16;
        longHexAppenderPad64[15] = a16;
        longHexAppenderPad64[16] = a16;

        LongHexAppender a20 = Numbers::appendLongHex20Pad64;
        longHexAppenderPad64[17] = a20;
        longHexAppenderPad64[18] = a20;
        longHexAppenderPad64[19] = a20;
        longHexAppenderPad64[20] = a20;

        LongHexAppender a24 = Numbers::appendLongHex24Pad64;
        longHexAppenderPad64[21] = a24;
        longHexAppenderPad64[22] = a24;
        longHexAppenderPad64[23] = a24;
        longHexAppenderPad64[24] = a24;

        LongHexAppender a28 = Numbers::appendLongHex28Pad64;
        longHexAppenderPad64[25] = a28;
        longHexAppenderPad64[26] = a28;
        longHexAppenderPad64[27] = a28;
        longHexAppenderPad64[28] = a28;

        LongHexAppender a32 = Numbers::appendLongHex32Pad64;
        longHexAppenderPad64[29] = a32;
        longHexAppenderPad64[30] = a32;
        longHexAppenderPad64[31] = a32;
        longHexAppenderPad64[32] = a32;

        LongHexAppender a36 = Numbers::appendLongHex36Pad64;
        longHexAppenderPad64[33] = a36;
        longHexAppenderPad64[34] = a36;
        longHexAppenderPad64[35] = a36;
        longHexAppenderPad64[36] = a36;

        LongHexAppender a40 = Numbers::appendLongHex40Pad64;
        longHexAppenderPad64[37] = a40;
        longHexAppenderPad64[38] = a40;
        longHexAppenderPad64[39] = a40;
        longHexAppenderPad64[40] = a40;

        LongHexAppender a44 = Numbers::appendLongHex44Pad64;
        longHexAppenderPad64[41] = a44;
        longHexAppenderPad64[42] = a44;
        longHexAppenderPad64[43] = a44;
        longHexAppenderPad64[44] = a44;

        LongHexAppender a48 = Numbers::appendLongHex48Pad64;
        longHexAppenderPad64[45] = a48;
        longHexAppenderPad64[46] = a48;
        longHexAppenderPad64[47] = a48;
        longHexAppenderPad64[48] = a48;

        LongHexAppender a52 = Numbers::appendLongHex52Pad64;
        longHexAppenderPad64[49] = a52;
        longHexAppenderPad64[50] = a52;
        longHexAppenderPad64[51] = a52;
        longHexAppenderPad64[52] = a52;

        LongHexAppender a56 = Numbers::appendLongHex56Pad64;
        longHexAppenderPad64[53] = a56;
        longHexAppenderPad64[54] = a56;
        longHexAppenderPad64[55] = a56;
        longHexAppenderPad64[56] = a56;

        LongHexAppender a60 = Numbers::appendLongHex60;
        longHexAppenderPad64[57] = a60;
        longHexAppenderPad64[58] = a60;
        longHexAppenderPad64[59] = a60;
        longHexAppenderPad64[60] = a60;

        LongHexAppender a64 = Numbers::appendLongHex64;
        longHexAppenderPad64[61] = a64;
        longHexAppenderPad64[62] = a64;
        longHexAppenderPad64[63] = a64;
        longHexAppenderPad64[64] = a64;
    }

    private Numbers() {
    }

    public static void append(CharSink sink, final double value, int scale) {
        double d = value;
        if (d == Double.POSITIVE_INFINITY) {
            sink.put("Infinity");
            return;
        }

        if (d == Double.NEGATIVE_INFINITY) {
            sink.put("-Infinity");
            return;
        }

        if (d != d) {
            sink.put("NaN");
            return;
        }

        if (value < 0) {
            sink.put('-');
            d = -value;
        }

        long factor = pow10[scale];
        long scaled = (long) (d * factor + 0.5);
        int targetScale = scale;
        //noinspection StatementWithEmptyBody
        while (++targetScale < 20 && pow10[targetScale] <= scaled) {
        }

        // factor overflow, fallback to slow method rather than throwing exception
        if (targetScale == 20) {
            sink.put(Double.toString(d));
            return;
        }

        factor = pow10[targetScale - 1];
        while (targetScale > 0) {
            if (targetScale-- == scale) {
                sink.put('.');
            }
            sink.put((char) ('0' + scaled / factor % 10));
            factor /= 10;
        }
    }

    public static void append(CharSink sink, final float value, int scale) {
        float f = value;
        if (f == Float.POSITIVE_INFINITY) {
            sink.put("Infinity");
            return;
        }

        if (f == Float.NEGATIVE_INFINITY) {
            sink.put("-Infinity");
            return;
        }

        if (f != f) {
            sink.put("NaN");
            return;
        }

        if (f < 0) {
            sink.put('-');
            f = -f;
        }
        int factor = (int) pow10[scale];
        int scaled = (int) (f * factor + 0.5);
        int targetScale = scale + 1;
        int z;
        while (targetScale < 11 && (z = factor * 10) <= scaled) {
            factor = z;
            targetScale++;
        }

        if (targetScale == 11) {
            sink.put(Float.toString(f));
            return;
        }

        while (targetScale > 0) {
            if (targetScale-- == scale) {
                sink.put('.');
            }
            sink.put((char) ('0' + scaled / factor % 10));
            factor /= 10;
        }
    }

    public static void append(CharSink sink, final int value) {
        int i = value;
        if (i < 0) {
            if (i == Integer.MIN_VALUE) {
                sink.put("NaN");
                return;
            }
            sink.put('-');
            i = -i;
        }
        if (i < 10) {
            sink.put((char) ('0' + i));
        } else if (i < 100) {  // two
            appendInt2(sink, i);
        } else if (i < 1000) { // three
            appendInt3(sink, i);
        } else if (i < 10000) { // four
            appendInt4(sink, i);
        } else if (i < 100000) { // five
            appendInt5(sink, i);
        } else if (i < 1000000) { // six
            appendInt6(sink, i);
        } else if (i < 10000000) { // seven
            appendInt7(sink, i);
        } else if (i < 100000000) { // eight
            appendInt8(sink, i);
        } else if (i < 1000000000) { // nine
            appendInt9(sink, i);
        } else {
            // ten
            appendInt10(sink, i);
        }
    }

    public static void append(CharSink sink, final long value) {
        long i = value;
        if (i < 0) {
            if (i == Long.MIN_VALUE) {
                sink.put("NaN");
                return;
            }
            sink.put('-');
            i = -i;
        }

        if (i < 10) {
            sink.put((char) ('0' + i));
        } else if (i < 100) {  // two
            appendLong2(sink, i);
        } else if (i < 1000) { // three
            appendLong3(sink, i);
        } else if (i < 10000) { // four
            appendLong4(sink, i);
        } else if (i < 100000) { // five
            appendLong5(sink, i);
        } else if (i < 1000000) { // six
            appendLong6(sink, i);
        } else if (i < 10000000) { // seven
            appendLong7(sink, i);
        } else if (i < 100000000) { // eight
            appendLong8(sink, i);
        } else if (i < 1000000000) { // nine
            appendLong9(sink, i);
        } else if (i < 10000000000L) {
            appendLong10(sink, i);
        } else if (i < 100000000000L) { //  eleven
            appendLong11(sink, i);
        } else if (i < 1000000000000L) { //  twelve
            appendLong12(sink, i);
        } else if (i < 10000000000000L) { //  thirteen
            appendLong13(sink, i);
        } else if (i < 100000000000000L) { //  fourteen
            appendLong14(sink, i);
        } else if (i < 1000000000000000L) { //  fifteen
            appendLong15(sink, i);
        } else if (i < 10000000000000000L) { //  sixteen
            appendLong16(sink, i);
        } else if (i < 100000000000000000L) { //  seventeen
            appendLong17(sink, i);
        } else if (i < 1000000000000000000L) { //  eighteen
            appendLong18(sink, i);
        } else { //  nineteen
            appendLong19(sink, i);
        }
    }

    public static void appendHex(CharSink sink, final int value) {
        int i = value;
        if (i < 0) {
            if (i == Integer.MIN_VALUE) {
                sink.put("NaN");
                return;
            }
            sink.put('-');
            i = -i;
        }
        int c;
        if (i < 0x10) {
            sink.put('0');
            sink.put(hexDigits[i]);
        } else if (i < 0x100) {  // two
            sink.put(hexDigits[i / 0x10]);
            sink.put(hexDigits[i % 0x10]);
        } else if (i < 0x1000) { // three
            sink.put('0');
            sink.put(hexDigits[i / 0x100]);
            sink.put(hexDigits[(c = i % 0x100) / 0x10]);
            sink.put(hexDigits[c % 0x10]);
        } else if (i < 0x10000) { // four
            sink.put(hexDigits[i / 0x1000]);
            sink.put(hexDigits[(c = i % 0x1000) / 0x100]);
            sink.put(hexDigits[(c = c % 0x100) / 0x10]);
            sink.put(hexDigits[c % 0x10]);
        } else if (i < 0x100000) { // five
            sink.put('0');
            sink.put(hexDigits[i / 0x10000]);
            sink.put(hexDigits[(c = i % 0x10000) / 0x1000]);
            sink.put(hexDigits[(c = c % 0x1000) / 0x100]);
            sink.put(hexDigits[(c = c % 0x100) / 0x10]);
            sink.put(hexDigits[c % 0x10]);
        } else if (i < 0x1000000) { // six
            sink.put(hexDigits[i / 0x100000]);
            sink.put(hexDigits[(c = i % 0x100000) / 0x10000]);
            sink.put(hexDigits[(c = c % 0x10000) / 0x1000]);
            sink.put(hexDigits[(c = c % 0x1000) / 0x100]);
            sink.put(hexDigits[(c = c % 0x100) / 0x10]);
            sink.put(hexDigits[c % 0x10]);
        } else if (i < 0x10000000) { // seven
            sink.put('0');
            sink.put(hexDigits[i / 0x1000000]);
            sink.put(hexDigits[(c = i % 0x1000000) / 0x100000]);
            sink.put(hexDigits[(c = c % 0x100000) / 0x10000]);
            sink.put(hexDigits[(c = c % 0x10000) / 0x1000]);
            sink.put(hexDigits[(c = c % 0x1000) / 0x100]);
            sink.put(hexDigits[(c = c % 0x100) / 0x10]);
            sink.put(hexDigits[c % 0x10]);
        } else { // eight
            sink.put(hexDigits[i / 0x10000000]);
            sink.put(hexDigits[(c = i % 0x10000000) / 0x1000000]);
            sink.put(hexDigits[(c = c % 0x1000000) / 0x100000]);
            sink.put(hexDigits[(c = c % 0x100000) / 0x10000]);
            sink.put(hexDigits[(c = c % 0x10000) / 0x1000]);
            sink.put(hexDigits[(c = c % 0x1000) / 0x100]);
            sink.put(hexDigits[(c = c % 0x100) / 0x10]);
            sink.put(hexDigits[c % 0x10]);
        }
    }

    public static void appendHex(CharSink sink, final long value, boolean pad) {
        if (value == Integer.MIN_VALUE) {
            sink.put("NaN");
            return;
        }
        int bit = 64 - Long.numberOfLeadingZeros(value - 1);
        LongHexAppender[] array = pad ? longHexAppenderPad64 : longHexAppender;
        array[bit].append(sink, value);
    }

    private static void appendLongHex4(CharSink sink, long value) {
        appendLongHexPad(sink, hexDigits[(int) ((value) & 0xf)]);
    }

    private static void appendLongHex4Pad64(CharSink sink, long value) {
        sink.put("00000000000000");
        appendLongHex4(sink, value);
    }

    private static void appendLongHex64(CharSink sink, long value) {
        sink.put(hexDigits[(int) ((value >> 60) & 0xf)]);
        sink.put(hexDigits[(int) ((value >> 56) & 0xf)]);
        appendLongHex56(sink, value);
    }

    private static void appendLongHex60(CharSink sink, long value) {
        appendLongHexPad(sink, hexDigits[(int) ((value >> 56) & 0xf)]);
        appendLongHex56(sink, value);
    }

    private static void appendLongHex56Pad64(CharSink sink, long value) {
        sink.put("00");
        appendLongHex56(sink, value);
    }

    private static void appendLongHex56(CharSink sink, long value) {
        sink.put(hexDigits[(int) ((value >> 52) & 0xf)]);
        sink.put(hexDigits[(int) ((value >> 48) & 0xf)]);
        appendLongHex48(sink, value);
    }

    private static void appendLongHex52Pad64(CharSink sink, long value) {
        sink.put("00");
        appendLongHex52(sink, value);
    }

    private static void appendLongHex52(CharSink sink, long value) {
        appendLongHexPad(sink, hexDigits[(int) ((value >> 48) & 0xf)]);
        appendLongHex48(sink, value);
    }

    private static void appendLongHex48Pad64(CharSink sink, long value) {
        sink.put("0000");
        appendLongHex48(sink, value);
    }

    private static void appendLongHex48(CharSink sink, long value) {
        sink.put(hexDigits[(int) ((value >> 44) & 0xf)]);
        sink.put(hexDigits[(int) ((value >> 40) & 0xf)]);
        appendLongHex40(sink, value);
    }

    private static void appendLongHex44Pad64(CharSink sink, long value) {
        sink.put("0000");
        appendLongHex44(sink, value);
    }

    private static void appendLongHex44(CharSink sink, long value) {
        appendLongHexPad(sink, hexDigits[(int) ((value >> 40) & 0xf)]);
        appendLongHex40(sink, value);
    }

    private static void appendLongHex40(CharSink sink, long value) {
        sink.put(hexDigits[(int) ((value >> 36) & 0xf)]);
        sink.put(hexDigits[(int) ((value >> 32) & 0xf)]);
        appendLongHex32(sink, value);
    }

    private static void appendLongHex28Pad64(CharSink sink, long value) {
        sink.put("00000000");
        appendLongHex28(sink, value);
    }

    private static void appendLongHex28(CharSink sink, long value) {
        appendLongHexPad(sink, hexDigits[(int) ((value >> 24) & 0xf)]);
        appendLongHex24(sink, value);
    }

    private static void appendLongHex20Pad64(CharSink sink, long value) {
        sink.put("0000000000");
        appendLongHex20(sink, value);
    }

    private static void appendLongHex20(CharSink sink, long value) {
        appendLongHexPad(sink, hexDigits[(int) ((value >> 16) & 0xf)]);
        appendLongHex16(sink, value);
    }

    private static void appendLongHex12Pad64(CharSink sink, long value) {
        sink.put("000000000000");
        appendLongHex12(sink, value);
    }

    private static void appendLongHex12(CharSink sink, long value) {
        appendLongHexPad(sink, hexDigits[(int) ((value >> 8) & 0xf)]);
        appendLongHex8(sink, value);
    }

    private static void appendLongHex36Pad64(CharSink sink, long value) {
        sink.put("000000");
        appendLongHex36(sink, value);
    }

    private static void appendLongHex40Pad64(CharSink sink, long value) {
        sink.put("000000");
        appendLongHex40(sink, value);
    }

    private static void appendLongHex36(CharSink sink, long value) {
        appendLongHexPad(sink, hexDigits[(int) ((value >> 32) & 0xf)]);
        appendLongHex32(sink, value);
    }

    private static void appendLongHex32Pad64(CharSink sink, long value) {
        sink.put("00000000");
        appendLongHex32(sink, value);
    }

    private static void appendLongHex32(CharSink sink, long value) {
        sink.put(hexDigits[(int) ((value >> 28) & 0xf)]);
        sink.put(hexDigits[(int) ((value >> 24) & 0xf)]);
        appendLongHex24(sink, value);
    }

    private static void appendLongHex24Pad64(CharSink sink, long value) {
        sink.put("0000000000");
        appendLongHex24(sink, value);
    }

    private static void appendLongHex24(CharSink sink, long value) {
        sink.put(hexDigits[(int) ((value >> 20) & 0xf)]);
        sink.put(hexDigits[(int) ((value >> 16) & 0xf)]);
        appendLongHex16(sink, value);
    }

    private static void appendLongHex16Pad64(CharSink sink, long value) {
        sink.put("000000000000");
        appendLongHex16(sink, value);
    }

    private static void appendLongHex16(CharSink sink, long value) {
        sink.put(hexDigits[(int) ((value >> 12) & 0xf)]);
        sink.put(hexDigits[(int) ((value >> 8) & 0xf)]);
        appendLongHex8(sink, value);
    }

    private static void appendLongHex8Pad64(CharSink sink, long value) {
        sink.put("00000000000000");
        appendLongHex8(sink, value);
    }

    private static void appendLongHex8(CharSink sink, long value) {
        sink.put(hexDigits[(int) ((value >> 4) & 0xf)]);
        sink.put(hexDigits[(int) ((value) & 0xf)]);
    }

    private static void appendLongHexPad(CharSink sink, char hexDigit) {
        sink.put('0');
        sink.put(hexDigit);
    }

    public static void appendHexPadded(CharSink sink, final int value) {
        int i = value;
        if (i < 0) {
            if (i == Integer.MIN_VALUE) {
                sink.put("NaN");
                return;
            }
            sink.put('-');
            i = -i;
        }
        int c;
        if (i < 0x10) {
            sink.put('0');
            sink.put('0');
            sink.put('0');
            sink.put('0');
            sink.put('0');
            sink.put('0');
            sink.put('0');
            sink.put(hexDigits[i]);
        } else if (i < 0x100) {  // two
            sink.put('0');
            sink.put('0');
            sink.put('0');
            sink.put('0');
            sink.put('0');
            sink.put('0');
            sink.put(hexDigits[i / 0x10]);
            sink.put(hexDigits[i % 0x10]);
        } else if (i < 0x1000) { // three
            sink.put('0');
            sink.put('0');
            sink.put('0');
            sink.put('0');
            sink.put('0');
            sink.put(hexDigits[i / 0x100]);
            sink.put(hexDigits[(c = i % 0x100) / 0x10]);
            sink.put(hexDigits[c % 0x10]);
        } else if (i < 0x10000) { // four
            sink.put('0');
            sink.put('0');
            sink.put('0');
            sink.put('0');
            sink.put(hexDigits[i / 0x1000]);
            sink.put(hexDigits[(c = i % 0x1000) / 0x100]);
            sink.put(hexDigits[(c = c % 0x100) / 0x10]);
            sink.put(hexDigits[c % 0x10]);
        } else if (i < 0x100000) { // five
            sink.put('0');
            sink.put('0');
            sink.put('0');
            sink.put(hexDigits[i / 0x10000]);
            sink.put(hexDigits[(c = i % 0x10000) / 0x1000]);
            sink.put(hexDigits[(c = c % 0x1000) / 0x100]);
            sink.put(hexDigits[(c = c % 0x100) / 0x10]);
            sink.put(hexDigits[c % 0x10]);
        } else if (i < 0x1000000) { // six
            sink.put('0');
            sink.put('0');
            sink.put(hexDigits[i / 0x100000]);
            sink.put(hexDigits[(c = i % 0x100000) / 0x10000]);
            sink.put(hexDigits[(c = c % 0x10000) / 0x1000]);
            sink.put(hexDigits[(c = c % 0x1000) / 0x100]);
            sink.put(hexDigits[(c = c % 0x100) / 0x10]);
            sink.put(hexDigits[c % 0x10]);
        } else if (i < 0x10000000) { // seven
            sink.put('0');
            sink.put(hexDigits[i / 0x1000000]);
            sink.put(hexDigits[(c = i % 0x1000000) / 0x100000]);
            sink.put(hexDigits[(c = c % 0x100000) / 0x10000]);
            sink.put(hexDigits[(c = c % 0x10000) / 0x1000]);
            sink.put(hexDigits[(c = c % 0x1000) / 0x100]);
            sink.put(hexDigits[(c = c % 0x100) / 0x10]);
            sink.put(hexDigits[c % 0x10]);
        } else { // eight
            sink.put(hexDigits[i / 0x10000000]);
            sink.put(hexDigits[(c = i % 0x10000000) / 0x1000000]);
            sink.put(hexDigits[(c = c % 0x1000000) / 0x100000]);
            sink.put(hexDigits[(c = c % 0x100000) / 0x10000]);
            sink.put(hexDigits[(c = c % 0x10000) / 0x1000]);
            sink.put(hexDigits[(c = c % 0x1000) / 0x100]);
            sink.put(hexDigits[(c = c % 0x100) / 0x10]);
            sink.put(hexDigits[c % 0x10]);
        }
    }

    public static void appendTrim(CharSink sink, double value, final int inScale) {
        double d = value;
        int scale = inScale;
        if (d == Double.POSITIVE_INFINITY) {
            sink.put("Infinity");
            return;
        }

        if (d == Double.NEGATIVE_INFINITY) {
            sink.put("-Infinity");
            return;
        }

        if (d != d) {
            sink.put("NaN");
            return;
        }

        if (d == 0d) {
            sink.put('0');
            return;
        }

        if (d < 0) {
            sink.put('-');
            d = -d;
        }

        long scaled = (long) (d * pow10[scale] + 0.5);

        // adjust scale to remove trailing zeroes
        int k = 1;
        while (scaled % pow10[k] == 0 && ((double) scaled) / pow10[k] > d) {
            k++;
        }
        scale = scale - k + 1;

        long factor = pow10[scale];
        scaled = (long) (d * factor + 0.5);

        int targetScale = scale + 1;
        while (targetScale < 20 && pow10[targetScale] <= scaled) {
            factor = pow10[targetScale++];
        }


        // factor overflow, fallback to slow method rather than throwing exception
        if (targetScale == 20) {
            sink.put(Double.toString(d));
            return;
        }

        while (targetScale > 0) {
            if (targetScale-- == scale) {
                sink.put('.');
            }
            sink.put((char) ('0' + scaled / factor % 10));
            factor /= 10;
        }
    }

    public static int ceilPow2(int value) {
        int i = value;
        if ((i != 0) && (i & (i - 1)) > 0) {
            i |= (i >>> 1);
            i |= (i >>> 2);
            i |= (i >>> 4);
            i |= (i >>> 8);
            i |= (i >>> 16);
            i++;

            if (i < 0) {
                i >>>= 1;
            }
        }

        return i;
    }

    public static long ceilPow2(long value) {
        long i = value;
        if ((i != 0) && (i & (i - 1)) > 0) {
            i |= (i >>> 1);
            i |= (i >>> 2);
            i |= (i >>> 4);
            i |= (i >>> 8);
            i |= (i >>> 16);
            i |= (i >>> 32);
            i++;

            if (i < 0) {
                i >>>= 1;
            }
        }
        return i;
    }

    public static int compare(double a, double b) {
        if (a < b) {
            return -1;
        }

        if (a > b) {
            return 1;
        }

        // Cannot use doubleToRawLongBits because of possibility of NaNs.
        long thisBits = Double.doubleToLongBits(1);
        long anotherBits = Double.doubleToLongBits(b);

        // Values are equal
        // (-0.0, 0.0) or (!NaN, NaN)
        return Long.compare(anotherBits, thisBits);
    }

    public static int compare(float a, float b) {
        if (a < b) {
            return -1;
        }
        if (a > b) {
            return 1;
        }

        // Cannot use floatToRawIntBits because of possibility of NaNs.
        int thisBits = Float.floatToIntBits(a);
        int anotherBits = Float.floatToIntBits(b);

        // Values are equal
        // (-0.0, 0.0) or (!NaN, NaN)
        return Integer.compare(anotherBits, thisBits);                          // (0.0, -0.0) or (NaN, !NaN)
    }

    public static int decodeHighInt(long val) {
        return (int) (val >> 32);
    }

    public static int decodeLowInt(long val) {
        return (int) (val & 0xffffffffL);
    }

    public static long encodeLowHighInts(int low, int high) {
        return ((Integer.toUnsignedLong(high)) << 32L) | Integer.toUnsignedLong(low);
    }

    public static int hexToDecimal(int c) throws NumericException {
        int r = hexNumbers[c];
        if (r == -1) {
            throw NumericException.INSTANCE;
        }
        return r;
    }

    public static int msb(int value) {
        return 31 - Integer.numberOfLeadingZeros(value);
    }

    public static int msb(long value) {
        return 63 - Long.numberOfLeadingZeros(value);
    }

    public static double parseDouble(CharSequence sequence) throws NumericException {
        int lim = sequence.length();

        if (lim == 0) {
            throw NumericException.INSTANCE;
        }

        boolean negative = sequence.charAt(0) == '-';
        int i;
        if (negative) {
            i = 1;
        } else {
            i = 0;
        }

        if (i >= lim) {
            throw NumericException.INSTANCE;
        }

        switch (sequence.charAt(i)) {
            case 'N':
                return parseConst(sequence, i, lim, NaN, Double.NaN);
            case 'I':
                return parseConst(sequence, i, lim, INFINITY, negative ? Double.NEGATIVE_INFINITY : Double.POSITIVE_INFINITY);
            default:
                break;
        }

        long val = 0;
        int dp = -1;
        int dpe = lim;
        int exp = 0;
        out:
        for (; i < lim; i++) {
            final int c = sequence.charAt(i);
            switch (c) {
                case '.':
                    dp = i;
                    continue;
                case 'E':
                case 'e':
                    exp = parseInt(sequence, i + 1, lim);
                    if (dpe == lim) {
                        dpe = i;
                    }
                    break out;
                case 'D':
                case 'd':
                    if (i + 1 < lim || i == 0) {
                        throw NumericException.INSTANCE;
                    }
                    if (dpe == lim) {
                        dpe = i;
                    }
                    break out;
                default:
                    if (c < '0' || c > '9') {
                        throw NumericException.INSTANCE;
                    }

                    if (val < LONG_OVERFLOW_MAX) {
                        // val * 10 + (c - '0')
                        val = (val << 3) + (val << 1) + (c - '0');
                    } else if (dpe == lim) {
                        dpe = i;
                    }
                    break;
            }
        }

        exp = dp == -1 ? exp : exp - (dpe - dp - 1);

        if (exp > 308) {
            exp = 308;
        } else if (exp < -308) {
            exp = -308;
        }

        if (exp > -1) {
            return (negative ? -val : val) * pow10d[exp];
        } else {
            return (negative ? -val : val) * pow10dNeg[-exp];
        }
    }

    public static float parseFloat(CharSequence sequence) throws NumericException {
        int lim = sequence.length();

        int p = 0;
        if (lim == p) {
            throw NumericException.INSTANCE;
        }

        boolean negative = sequence.charAt(p) == '-';
        if (negative) {
            p++;
        }

        if (p >= lim) {
            throw NumericException.INSTANCE;
        }


        switch (sequence.charAt(p)) {
            case 'N':
                return parseFloatConst(sequence, p, lim, NaN, Float.NaN);
            case 'I':
                return parseFloatConst(sequence, p, lim, INFINITY, negative ? Float.NEGATIVE_INFINITY : Float.POSITIVE_INFINITY);
            default:
                break;
        }

        int val = 0;
        int dp = -1;
        int dpe = lim;
        int exp = 0;
        out:
        for (int i = p; i < lim; i++) {
            int c = sequence.charAt(i);
            switch (c) {
                case '.':
                    dp = i;
                    continue;
                case 'E':
                case 'e':
                    exp = parseInt(sequence, i + 1, lim);
                    if (dpe == lim) {
                        dpe = i;
                    }
                    break out;
                case 'F':
                case 'f':
                    if (i == 0 || i + 1 < lim) {
                        throw NumericException.INSTANCE;
                    }

                    if (dpe == lim) {
                        dpe = i;
                    }
                    break out;
                default:
                    if (c < '0' || c > '9') {
                        throw NumericException.INSTANCE;
                    }

                    if (val <= INT_OVERFLOW_MAX) {
                        // val * 10 + (c - '0')
                        val = (val << 3) + (val << 1) + (c - '0');
                    } else if (dpe == lim) {
                        dpe = i;
                    }
                    break;
            }
        }

        exp = dp == -1 ? exp : exp - (dpe - dp - 1);

        if (exp > 38) {
            exp = 38;
        } else if (exp < -38) {
            exp = -38;
        }

        if (exp > 0) {
            return (negative ? -val : val) * pow10f[exp];
        } else {
            return (negative ? -val : val) / pow10f[-exp];
        }
    }

    public static int parseHexInt(CharSequence sequence) throws NumericException {
        return parseHexInt(sequence, 0, sequence.length());
    }

    public static int parseHexInt(CharSequence sequence, int lo, int hi) throws NumericException {
        if (hi == 0) {
            throw NumericException.INSTANCE;
        }

        int val = 0;
        int r;
        for (int i = lo; i < hi; i++) {
            int c = sequence.charAt(i);
            int n = val << 4;
            r = n + hexToDecimal(c);
            val = r;
        }
        return val;
    }

    public static long parseHexLong(CharSequence sequence, int lo, int hi) throws NumericException {
        if (hi == 0) {
            throw NumericException.INSTANCE;
        }

        long val = 0;
        long r;
        for (int i = lo; i < hi; i++) {
            int c = sequence.charAt(i);
            long n = val << 4;

            r = n + hexToDecimal(c);
            val = r;
        }
        return val;
    }

    public static int parseInt(CharSequence sequence) throws NumericException {
        if (sequence == null) {
            throw NumericException.INSTANCE;
        }

        return parseInt0(sequence, 0, sequence.length());
    }

    public static int parseInt(CharSequence sequence, int p, int lim) throws NumericException {
        if (sequence == null) {
            throw NumericException.INSTANCE;
        }
        return parseInt0(sequence, p, lim);
    }

    public static int parseIntQuiet(CharSequence sequence) {
        try {
            if (sequence == null || Chars.equals("NaN", sequence)) {
                return Integer.MIN_VALUE;
            }
            return parseInt0(sequence, 0, sequence.length());
        } catch (NumericException e) {
            return Integer.MIN_VALUE;
        }

    }

    public static long parseIntSafely(CharSequence sequence, final int p, int lim) throws NumericException {

        if (lim == p) {
            throw NumericException.INSTANCE;
        }

        boolean negative = sequence.charAt(p) == '-';
        int i = p;
        if (negative) {
            i++;
        }

        if (i >= lim || notDigit(sequence.charAt(i))) {
            throw NumericException.INSTANCE;
        }

        int val = 0;
        for (; i < lim; i++) {
            char c = sequence.charAt(i);

            if (notDigit(c)) {
                break;
            }

            // val * 10 + (c - '0')
            int r = (val << 3) + (val << 1) - (c - '0');
            if (r > val) {
                throw NumericException.INSTANCE;
            }
            val = r;
        }

        if (val == Integer.MIN_VALUE && !negative) {
            throw NumericException.INSTANCE;
        }

        return encodeLowHighInts(negative ? val : -val, i - p);
    }

    public static int parseIntSize(CharSequence sequence) throws NumericException {
        int lim = sequence.length();

        if (lim == 0) {
            throw NumericException.INSTANCE;
        }

        boolean negative = sequence.charAt(0) == '-';
        int i = 0;
        if (negative) {
            i++;
        }

        if (i >= lim) {
            throw NumericException.INSTANCE;
        }

        int val = 0;
        int r;
        EX:
        for (; i < lim; i++) {
            int c = sequence.charAt(i);
            if (c < '0' || c > '9') {
                if (i == lim - 1) {
                    switch (c) {
                        case 'K':
                        case 'k':
                            r = val * 1024;
                            if (r > val) {
                                throw NumericException.INSTANCE;
                            }
                            val = r;
                            break EX;
                        case 'M':
                        case 'm':
                            r = val * 1024 * 1024;
                            if (r > val) {
                                throw NumericException.INSTANCE;
                            }
                            val = r;
                            break EX;
                        default:
                            break;
                    }
                }
                throw NumericException.INSTANCE;
            }
            // val * 10 + (c - '0')
            r = (val << 3) + (val << 1) - (c - '0');
            if (r > val) {
                throw NumericException.INSTANCE;
            }
            val = r;
        }

        if (val == Integer.MIN_VALUE && !negative) {
            throw NumericException.INSTANCE;
        }
        return negative ? val : -val;
    }

    public static long parseLong(CharSequence sequence) throws NumericException {
        if (sequence == null) {
            throw NumericException.INSTANCE;
        }
        return parseLong0(sequence, 0, sequence.length());
    }

    public static long parseLong(CharSequence sequence, int p, int lim) throws NumericException {
        if (sequence == null) {
            throw NumericException.INSTANCE;
        }
        return parseLong0(sequence, p, lim);
    }

    public static long parseLongQuiet(CharSequence sequence) {
        if (sequence == null) {
            return Long.MIN_VALUE;
        }
        try {
            return parseLong0(sequence, 0, sequence.length());
        } catch (NumericException e) {
            return Long.MIN_VALUE;
        }
    }

    public static long parseLongSize(CharSequence sequence) throws NumericException {
        int lim = sequence.length();

        if (lim == 0) {
            throw NumericException.INSTANCE;
        }

        boolean negative = sequence.charAt(0) == '-';
        int i = 0;
        if (negative) {
            i++;
        }

        if (i >= lim) {
            throw NumericException.INSTANCE;
        }

        long val = 0;
        long r;
        EX:
        for (; i < lim; i++) {
            int c = sequence.charAt(i);
            if (c < '0' || c > '9') {
                if (i == lim - 1) {
                    switch (c) {
                        case 'K':
                        case 'k':
                            r = val * 1024L;
                            if (r > val) {
                                throw NumericException.INSTANCE;
                            }
                            val = r;
                            break EX;
                        case 'M':
                        case 'm':
                            r = val * 1024L * 1024L;
                            if (r > val) {
                                throw NumericException.INSTANCE;
                            }
                            val = r;
                            break EX;
                        case 'G':
                        case 'g':
                            r = val * 1024L * 1024L * 1024L;
                            if (r > val) {
                                throw NumericException.INSTANCE;
                            }
                            val = r;
                            break EX;
                        default:
                            break;
                    }
                }
                throw NumericException.INSTANCE;
            }
            // val * 10 + (c - '0')
            r = (val << 3) + (val << 1) - (c - '0');
            if (r > val) {
                throw NumericException.INSTANCE;
            }
            val = r;
        }

        if (val == Long.MIN_VALUE && !negative) {
            throw NumericException.INSTANCE;
        }
        return negative ? val : -val;
    }

    public static double roundDown(double value, int scale) throws NumericException {
        if (scale < pow10max && scale > -1) {
            return roundDown0(value, scale);
        }
        throw NumericException.INSTANCE;
    }

    public static double roundHalfDown(double value, int scale) throws NumericException {
        if (scale + 2 < pow10max) {
            return value > 0 ? roundHalfDown0(value, scale) : -roundHalfDown0(-value, scale);
        }
        throw NumericException.INSTANCE;
    }

    public static double roundHalfEven(double value, int scale) throws NumericException {
        if (scale + 2 < pow10max && scale > -1) {
            return value > 0 ? roundHalfEven0(value, scale) : -roundHalfEven0(-value, scale);
        }
        throw NumericException.INSTANCE;
    }

    public static double roundHalfUp(double value, int scale) throws NumericException {
        if (scale + 2 < pow10max) {
            return value > 0 ? roundHalfUp0(value, scale) : -roundHalfUp0(-value, scale);
        }
        throw NumericException.INSTANCE;
    }

    public static double roundUp(double value, int scale) throws NumericException {
        if (scale < pow10max && scale > -1) {
            return roundUp0(value, scale);
        }
        throw NumericException.INSTANCE;
    }

    private static void appendInt10(CharSink sink, int i) {
        int c;
        sink.put((char) ('0' + i / 1000000000));
        sink.put((char) ('0' + (c = i % 1000000000) / 100000000));
        sink.put((char) ('0' + (c %= 100000000) / 10000000));
        sink.put((char) ('0' + (c %= 10000000) / 1000000));
        sink.put((char) ('0' + (c %= 1000000) / 100000));
        sink.put((char) ('0' + (c %= 100000) / 10000));
        sink.put((char) ('0' + (c %= 10000) / 1000));
        sink.put((char) ('0' + (c %= 1000) / 100));
        sink.put((char) ('0' + (c %= 100) / 10));
        sink.put((char) ('0' + (c % 10)));
    }

    private static void appendInt9(CharSink sink, int i) {
        int c;
        sink.put((char) ('0' + i / 100000000));
        sink.put((char) ('0' + (c = i % 100000000) / 10000000));
        sink.put((char) ('0' + (c %= 10000000) / 1000000));
        sink.put((char) ('0' + (c %= 1000000) / 100000));
        sink.put((char) ('0' + (c %= 100000) / 10000));
        sink.put((char) ('0' + (c %= 10000) / 1000));
        sink.put((char) ('0' + (c %= 1000) / 100));
        sink.put((char) ('0' + (c %= 100) / 10));
        sink.put((char) ('0' + (c % 10)));
    }

    private static void appendInt8(CharSink sink, int i) {
        int c;
        sink.put((char) ('0' + i / 10000000));
        sink.put((char) ('0' + (c = i % 10000000) / 1000000));
        sink.put((char) ('0' + (c %= 1000000) / 100000));
        sink.put((char) ('0' + (c %= 100000) / 10000));
        sink.put((char) ('0' + (c %= 10000) / 1000));
        sink.put((char) ('0' + (c %= 1000) / 100));
        sink.put((char) ('0' + (c %= 100) / 10));
        sink.put((char) ('0' + (c % 10)));
    }

    private static void appendInt7(CharSink sink, int i) {
        int c;
        sink.put((char) ('0' + i / 1000000));
        sink.put((char) ('0' + (c = i % 1000000) / 100000));
        sink.put((char) ('0' + (c %= 100000) / 10000));
        sink.put((char) ('0' + (c %= 10000) / 1000));
        sink.put((char) ('0' + (c %= 1000) / 100));
        sink.put((char) ('0' + (c %= 100) / 10));
        sink.put((char) ('0' + (c % 10)));
    }

    private static void appendInt6(CharSink sink, int i) {
        int c;
        sink.put((char) ('0' + i / 100000));
        sink.put((char) ('0' + (c = i % 100000) / 10000));
        sink.put((char) ('0' + (c %= 10000) / 1000));
        sink.put((char) ('0' + (c %= 1000) / 100));
        sink.put((char) ('0' + (c %= 100) / 10));
        sink.put((char) ('0' + (c % 10)));
    }

    private static void appendInt5(CharSink sink, int i) {
        int c;
        sink.put((char) ('0' + i / 10000));
        sink.put((char) ('0' + (c = i % 10000) / 1000));
        sink.put((char) ('0' + (c %= 1000) / 100));
        sink.put((char) ('0' + (c %= 100) / 10));
        sink.put((char) ('0' + (c % 10)));
    }

    private static void appendInt4(CharSink sink, int i) {
        int c;
        sink.put((char) ('0' + i / 1000));
        sink.put((char) ('0' + (c = i % 1000) / 100));
        sink.put((char) ('0' + (c %= 100) / 10));
        sink.put((char) ('0' + (c % 10)));
    }

    private static void appendInt3(CharSink sink, int i) {
        int c;
        sink.put((char) ('0' + i / 100));
        sink.put((char) ('0' + (c = i % 100) / 10));
        sink.put((char) ('0' + (c % 10)));
    }

    private static void appendInt2(CharSink sink, int i) {
        sink.put((char) ('0' + i / 10));
        sink.put((char) ('0' + i % 10));
    }

    private static void appendLong2(CharSink sink, long i) {
        sink.put((char) ('0' + i / 10));
        sink.put((char) ('0' + i % 10));
    }

    private static void appendLong9(CharSink sink, long i) {
        long c;
        sink.put((char) ('0' + i / 100000000));
        sink.put((char) ('0' + (c = i % 100000000) / 10000000));
        sink.put((char) ('0' + (c %= 10000000) / 1000000));
        sink.put((char) ('0' + (c %= 1000000) / 100000));
        sink.put((char) ('0' + (c %= 100000) / 10000));
        sink.put((char) ('0' + (c %= 10000) / 1000));
        sink.put((char) ('0' + (c %= 1000) / 100));
        sink.put((char) ('0' + (c %= 100) / 10));
        sink.put((char) ('0' + (c % 10)));
    }

    private static void appendLong8(CharSink sink, long i) {
        long c;
        sink.put((char) ('0' + i / 10000000));
        sink.put((char) ('0' + (c = i % 10000000) / 1000000));
        sink.put((char) ('0' + (c %= 1000000) / 100000));
        sink.put((char) ('0' + (c %= 100000) / 10000));
        sink.put((char) ('0' + (c %= 10000) / 1000));
        sink.put((char) ('0' + (c %= 1000) / 100));
        sink.put((char) ('0' + (c %= 100) / 10));
        sink.put((char) ('0' + (c % 10)));
    }

    private static void appendLong7(CharSink sink, long i) {
        long c;
        sink.put((char) ('0' + i / 1000000));
        sink.put((char) ('0' + (c = i % 1000000) / 100000));
        sink.put((char) ('0' + (c %= 100000) / 10000));
        sink.put((char) ('0' + (c %= 10000) / 1000));
        sink.put((char) ('0' + (c %= 1000) / 100));
        sink.put((char) ('0' + (c %= 100) / 10));
        sink.put((char) ('0' + (c % 10)));
    }

    private static void appendLong6(CharSink sink, long i) {
        long c;
        sink.put((char) ('0' + i / 100000));
        sink.put((char) ('0' + (c = i % 100000) / 10000));
        sink.put((char) ('0' + (c %= 10000) / 1000));
        sink.put((char) ('0' + (c %= 1000) / 100));
        sink.put((char) ('0' + (c %= 100) / 10));
        sink.put((char) ('0' + (c % 10)));
    }

    private static void appendLong5(CharSink sink, long i) {
        long c;
        sink.put((char) ('0' + i / 10000));
        sink.put((char) ('0' + (c = i % 10000) / 1000));
        sink.put((char) ('0' + (c %= 1000) / 100));
        sink.put((char) ('0' + (c %= 100) / 10));
        sink.put((char) ('0' + (c % 10)));
    }

    private static void appendLong4(CharSink sink, long i) {
        long c;
        sink.put((char) ('0' + i / 1000));
        sink.put((char) ('0' + (c = i % 1000) / 100));
        sink.put((char) ('0' + (c %= 100) / 10));
        sink.put((char) ('0' + (c % 10)));
    }

    private static void appendLong3(CharSink sink, long i) {
        long c;
        sink.put((char) ('0' + i / 100));
        sink.put((char) ('0' + (c = i % 100) / 10));
        sink.put((char) ('0' + (c % 10)));
    }

    private static boolean notDigit(char c) {
        return c < '0' || c > '9';
    }

    private static double roundHalfUp0(double value, int scale) {
        long val = (long) (value * pow10[scale + 2] + TOLERANCE);
        return val % 100 < 50 ? roundDown0(value, scale) : roundUp0(value, scale);
    }

    private static double roundHalfEven0(double value, int scale) {
        long val = (long) (value * pow10[scale + 2] + TOLERANCE);
        long remainder = val % 100;

        if (remainder < 50) {
            return roundDown0(value, scale);
        }

        if (remainder == 50 && ((long) (value * pow10[scale]) & 1) == 0) {
            return roundDown0(value, scale);
        }

        return roundUp0(value, scale);
    }

    private static double roundHalfDown0(double value, int scale) {
        long val = (long) (value * pow10[scale + 2] + TOLERANCE);
        return val % 100 > 50 ? roundUp0(value, scale) : roundDown0(value, scale);
    }

    private static double roundDown0(double value, int scale) {
        return value < 0 ? -roundDown00(-value, scale) : roundDown00(value, scale);
    }

    private static double roundUp0(double value, int scale) {
        return value < 0 ? -roundUp00(-value, scale) : roundUp00(value, scale);
    }

    private static double roundUp00(double value, int scale) {
        long powten = pow10[scale];
        return ((double) (long) (value * powten + 1 - TOLERANCE)) / powten;
    }


    //////////////////////

    private static double roundDown00(double value, int scale) {
        long powten = pow10[scale];
        return ((double) (long) (value * powten + TOLERANCE)) / powten;
    }

    private static void appendLong10(CharSink sink, long i) {
        long c;
        sink.put((char) ('0' + i / 1000000000L));
        sink.put((char) ('0' + (c = i % 1000000000L) / 100000000));
        sink.put((char) ('0' + (c %= 100000000) / 10000000));
        sink.put((char) ('0' + (c %= 10000000) / 1000000));
        sink.put((char) ('0' + (c %= 1000000) / 100000));
        sink.put((char) ('0' + (c %= 100000) / 10000));
        sink.put((char) ('0' + (c %= 10000) / 1000));
        sink.put((char) ('0' + (c %= 1000) / 100));
        sink.put((char) ('0' + (c %= 100) / 10));
        sink.put((char) ('0' + (c % 10)));
    }

    private static void appendLong11(CharSink sink, long i) {
        long c;
        sink.put((char) ('0' + i / 10000000000L));
        sink.put((char) ('0' + (c = i % 10000000000L) / 1000000000));
        sink.put((char) ('0' + (c %= 1000000000) / 100000000));
        sink.put((char) ('0' + (c %= 100000000) / 10000000));
        sink.put((char) ('0' + (c %= 10000000) / 1000000));
        sink.put((char) ('0' + (c %= 1000000) / 100000));
        sink.put((char) ('0' + (c %= 100000) / 10000));
        sink.put((char) ('0' + (c %= 10000) / 1000));
        sink.put((char) ('0' + (c %= 1000) / 100));
        sink.put((char) ('0' + (c %= 100) / 10));
        sink.put((char) ('0' + (c % 10)));
    }

    private static void appendLong12(CharSink sink, long i) {
        long c;
        sink.put((char) ('0' + i / 100000000000L));
        sink.put((char) ('0' + (c = i % 100000000000L) / 10000000000L));
        sink.put((char) ('0' + (c %= 10000000000L) / 1000000000));
        sink.put((char) ('0' + (c %= 1000000000) / 100000000));
        sink.put((char) ('0' + (c %= 100000000) / 10000000));
        sink.put((char) ('0' + (c %= 10000000) / 1000000));
        sink.put((char) ('0' + (c %= 1000000) / 100000));
        sink.put((char) ('0' + (c %= 100000) / 10000));
        sink.put((char) ('0' + (c %= 10000) / 1000));
        sink.put((char) ('0' + (c %= 1000) / 100));
        sink.put((char) ('0' + (c %= 100) / 10));
        sink.put((char) ('0' + (c % 10)));
    }

    private static void appendLong19(CharSink sink, long i) {
        long c;
        sink.put((char) ('0' + i / 1000000000000000000L));
        sink.put((char) ('0' + (c = i % 1000000000000000000L) / 100000000000000000L));
        sink.put((char) ('0' + (c %= 100000000000000000L) / 10000000000000000L));
        sink.put((char) ('0' + (c %= 10000000000000000L) / 1000000000000000L));
        sink.put((char) ('0' + (c %= 1000000000000000L) / 100000000000000L));
        sink.put((char) ('0' + (c %= 100000000000000L) / 10000000000000L));
        sink.put((char) ('0' + (c %= 10000000000000L) / 1000000000000L));
        sink.put((char) ('0' + (c %= 1000000000000L) / 100000000000L));
        sink.put((char) ('0' + (c %= 100000000000L) / 10000000000L));
        sink.put((char) ('0' + (c %= 10000000000L) / 1000000000));
        sink.put((char) ('0' + (c %= 1000000000) / 100000000));
        sink.put((char) ('0' + (c %= 100000000) / 10000000));
        sink.put((char) ('0' + (c %= 10000000) / 1000000));
        sink.put((char) ('0' + (c %= 1000000) / 100000));
        sink.put((char) ('0' + (c %= 100000) / 10000));
        sink.put((char) ('0' + (c %= 10000) / 1000));
        sink.put((char) ('0' + (c %= 1000) / 100));
        sink.put((char) ('0' + (c %= 100) / 10));
        sink.put((char) ('0' + (c % 10)));
    }

    private static void appendLong18(CharSink sink, long i) {
        long c;
        sink.put((char) ('0' + i / 100000000000000000L));
        sink.put((char) ('0' + (c = i % 100000000000000000L) / 10000000000000000L));
        sink.put((char) ('0' + (c %= 10000000000000000L) / 1000000000000000L));
        sink.put((char) ('0' + (c %= 1000000000000000L) / 100000000000000L));
        sink.put((char) ('0' + (c %= 100000000000000L) / 10000000000000L));
        sink.put((char) ('0' + (c %= 10000000000000L) / 1000000000000L));
        sink.put((char) ('0' + (c %= 1000000000000L) / 100000000000L));
        sink.put((char) ('0' + (c %= 100000000000L) / 10000000000L));
        sink.put((char) ('0' + (c %= 10000000000L) / 1000000000));
        sink.put((char) ('0' + (c %= 1000000000) / 100000000));
        sink.put((char) ('0' + (c %= 100000000) / 10000000));
        sink.put((char) ('0' + (c %= 10000000) / 1000000));
        sink.put((char) ('0' + (c %= 1000000) / 100000));
        sink.put((char) ('0' + (c %= 100000) / 10000));
        sink.put((char) ('0' + (c %= 10000) / 1000));
        sink.put((char) ('0' + (c %= 1000) / 100));
        sink.put((char) ('0' + (c %= 100) / 10));
        sink.put((char) ('0' + (c % 10)));
    }

    private static void appendLong17(CharSink sink, long i) {
        long c;
        sink.put((char) ('0' + i / 10000000000000000L));
        sink.put((char) ('0' + (c = i % 10000000000000000L) / 1000000000000000L));
        sink.put((char) ('0' + (c %= 1000000000000000L) / 100000000000000L));
        sink.put((char) ('0' + (c %= 100000000000000L) / 10000000000000L));
        sink.put((char) ('0' + (c %= 10000000000000L) / 1000000000000L));
        sink.put((char) ('0' + (c %= 1000000000000L) / 100000000000L));
        sink.put((char) ('0' + (c %= 100000000000L) / 10000000000L));
        sink.put((char) ('0' + (c %= 10000000000L) / 1000000000));
        sink.put((char) ('0' + (c %= 1000000000) / 100000000));
        sink.put((char) ('0' + (c %= 100000000) / 10000000));
        sink.put((char) ('0' + (c %= 10000000) / 1000000));
        sink.put((char) ('0' + (c %= 1000000) / 100000));
        sink.put((char) ('0' + (c %= 100000) / 10000));
        sink.put((char) ('0' + (c %= 10000) / 1000));
        sink.put((char) ('0' + (c %= 1000) / 100));
        sink.put((char) ('0' + (c %= 100) / 10));
        sink.put((char) ('0' + (c % 10)));
    }

    private static void appendLong16(CharSink sink, long i) {
        long c;
        sink.put((char) ('0' + i / 1000000000000000L));
        sink.put((char) ('0' + (c = i % 1000000000000000L) / 100000000000000L));
        sink.put((char) ('0' + (c %= 100000000000000L) / 10000000000000L));
        sink.put((char) ('0' + (c %= 10000000000000L) / 1000000000000L));
        sink.put((char) ('0' + (c %= 1000000000000L) / 100000000000L));
        sink.put((char) ('0' + (c %= 100000000000L) / 10000000000L));
        sink.put((char) ('0' + (c %= 10000000000L) / 1000000000));
        sink.put((char) ('0' + (c %= 1000000000) / 100000000));
        sink.put((char) ('0' + (c %= 100000000) / 10000000));
        sink.put((char) ('0' + (c %= 10000000) / 1000000));
        sink.put((char) ('0' + (c %= 1000000) / 100000));
        sink.put((char) ('0' + (c %= 100000) / 10000));
        sink.put((char) ('0' + (c %= 10000) / 1000));
        sink.put((char) ('0' + (c %= 1000) / 100));
        sink.put((char) ('0' + (c %= 100) / 10));
        sink.put((char) ('0' + (c % 10)));
    }

    private static void appendLong15(CharSink sink, long i) {
        long c;
        sink.put((char) ('0' + i / 100000000000000L));
        sink.put((char) ('0' + (c = i % 100000000000000L) / 10000000000000L));
        sink.put((char) ('0' + (c %= 10000000000000L) / 1000000000000L));
        sink.put((char) ('0' + (c %= 1000000000000L) / 100000000000L));
        sink.put((char) ('0' + (c %= 100000000000L) / 10000000000L));
        sink.put((char) ('0' + (c %= 10000000000L) / 1000000000));
        sink.put((char) ('0' + (c %= 1000000000) / 100000000));
        sink.put((char) ('0' + (c %= 100000000) / 10000000));
        sink.put((char) ('0' + (c %= 10000000) / 1000000));
        sink.put((char) ('0' + (c %= 1000000) / 100000));
        sink.put((char) ('0' + (c %= 100000) / 10000));
        sink.put((char) ('0' + (c %= 10000) / 1000));
        sink.put((char) ('0' + (c %= 1000) / 100));
        sink.put((char) ('0' + (c %= 100) / 10));
        sink.put((char) ('0' + (c % 10)));
    }

    private static void appendLong14(CharSink sink, long i) {
        long c;
        sink.put((char) ('0' + i / 10000000000000L));
        sink.put((char) ('0' + (c = i % 10000000000000L) / 1000000000000L));
        sink.put((char) ('0' + (c %= 1000000000000L) / 100000000000L));
        sink.put((char) ('0' + (c %= 100000000000L) / 10000000000L));
        sink.put((char) ('0' + (c %= 10000000000L) / 1000000000));
        sink.put((char) ('0' + (c %= 1000000000) / 100000000));
        sink.put((char) ('0' + (c %= 100000000) / 10000000));
        sink.put((char) ('0' + (c %= 10000000) / 1000000));
        sink.put((char) ('0' + (c %= 1000000) / 100000));
        sink.put((char) ('0' + (c %= 100000) / 10000));
        sink.put((char) ('0' + (c %= 10000) / 1000));
        sink.put((char) ('0' + (c %= 1000) / 100));
        sink.put((char) ('0' + (c %= 100) / 10));
        sink.put((char) ('0' + (c % 10)));
    }

    private static void appendLong13(CharSink sink, long i) {
        long c;
        sink.put((char) ('0' + i / 1000000000000L));
        sink.put((char) ('0' + (c = i % 1000000000000L) / 100000000000L));
        sink.put((char) ('0' + (c %= 100000000000L) / 10000000000L));
        sink.put((char) ('0' + (c %= 10000000000L) / 1000000000));
        sink.put((char) ('0' + (c %= 1000000000) / 100000000));
        sink.put((char) ('0' + (c %= 100000000) / 10000000));
        sink.put((char) ('0' + (c %= 10000000) / 1000000));
        sink.put((char) ('0' + (c %= 1000000) / 100000));
        sink.put((char) ('0' + (c %= 100000) / 10000));
        sink.put((char) ('0' + (c %= 10000) / 1000));
        sink.put((char) ('0' + (c %= 1000) / 100));
        sink.put((char) ('0' + (c %= 100) / 10));
        sink.put((char) ('0' + (c % 10)));
    }

    private static double parseConst(CharSequence sequence, int p, int lim, String target, double value) throws NumericException {
        validateConst(sequence, p, lim, target);
        return value;
    }

    private static void validateConst(CharSequence sequence, int p, int lim, String target) throws NumericException {
        int len = target.length();

        if (lim - p != len) {
            throw NumericException.INSTANCE;
        }

        for (int i = 0; i < len; i++) {
            if (sequence.charAt(p + i) != target.charAt(i)) {
                throw NumericException.INSTANCE;
            }
        }
    }

    private static float parseFloatConst(CharSequence sequence, int p, int lim, String target, float value) throws NumericException {
        validateConst(sequence, p, lim, target);
        return value;
    }

    private static int parseInt0(CharSequence sequence, final int p, int lim) throws NumericException {

        if (lim == p) {
            throw NumericException.INSTANCE;
        }

        boolean negative = sequence.charAt(p) == '-';
        int i = p;
        if (negative) {
            i++;
        }

        if (i >= lim) {
            throw NumericException.INSTANCE;
        }

        int val = 0;
        for (; i < lim; i++) {
            char c = sequence.charAt(i);
            if (c < '0' || c > '9') {
                throw NumericException.INSTANCE;
            }
            // val * 10 + (c - '0')
            if (val < (Integer.MIN_VALUE / 10)) {
                throw NumericException.INSTANCE;
            }
            int r = (val << 3) + (val << 1) - (c - '0');
            if (r > val) {
                throw NumericException.INSTANCE;
            }
            val = r;
        }

        if (val == Integer.MIN_VALUE && !negative) {
            throw NumericException.INSTANCE;
        }
        return negative ? val : -val;
    }

    private static long parseLong0(CharSequence sequence, final int p, int lim) throws NumericException {

        if (lim == p) {
            throw NumericException.INSTANCE;
        }

        boolean negative = sequence.charAt(p) == '-';

        int i = p;
        if (negative) {
            i++;
        }

        if (i >= lim) {
            throw NumericException.INSTANCE;
        }

        long val = 0;
        for (; i < lim; i++) {
            int c = sequence.charAt(i);
            if (c == 'L' || c == 'l') {
                if (i == 0 || i + 1 < lim) {
                    throw NumericException.INSTANCE;
                }
                break;
            }
            if (c < '0' || c > '9') {
                throw NumericException.INSTANCE;
            }
            // val * 10 + (c - '0')
            long r = (val << 3) + (val << 1) - (c - '0');
            if (r > val) {
                throw NumericException.INSTANCE;
            }
            val = r;
        }

        if (val == Long.MIN_VALUE && !negative) {
            throw NumericException.INSTANCE;
        }
        return negative ? val : -val;
    }

    public static void appendLong256(long a, long b, long c, long d, CharSink sink) {
        if (a == -1 && b == -1 && c == -1 && d == -1) {
            return;
        }
        sink.put("0x");
        if (d != 0) {
            appendLong256Four(a, b, c, d, sink);
            return;
        }

        if (c != 0) {
            appendLong256Three(a, b, c, sink);
            return;
        }

        if (b != 0) {
            appendLong256Two(a, b, sink);
            return;
        }

        appendHex(sink, a, false);
    }

    private static void appendLong256Two(long a, long b, CharSink sink) {
        appendHex(sink, b, false);
        appendHex(sink, a, true);
    }

    private static void appendLong256Three(long a, long b, long c, CharSink sink) {
        appendLong256Two(b, c, sink);
        appendHex(sink, a, true);
    }

    private static void appendLong256Four(long a, long b, long c, long d, CharSink sink) {
        appendLong256Three(b, c, d, sink);
        appendHex(sink, a, true);
    }

    @FunctionalInterface
    private interface LongHexAppender {
        void append(CharSink sink, long value);
    }
}