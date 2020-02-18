package com.example.myruns5;

class WekaClassifier {

    public static double classify(Object[] i)
            throws Exception {

        double p = Double.NaN;
        p = WekaClassifier.N2996b0e10(i);
        return p;
    }
    static double N2996b0e10(Object []i) {
        double p = Double.NaN;
        if (i[0] == null) {
            p = 0;
        } else if (((Double) i[0]).doubleValue() <= 109.024782) {
            p = 0;
        } else if (((Double) i[0]).doubleValue() > 109.024782) {
            p = WekaClassifier.N72826d4c1(i);
        }
        return p;
    }
    static double N72826d4c1(Object []i) {
        double p = Double.NaN;
        if (i[0] == null) {
            p = 1;
        } else if (((Double) i[0]).doubleValue() <= 519.260914) {
            p = WekaClassifier.N48c439892(i);
        } else if (((Double) i[0]).doubleValue() > 519.260914) {
            p = WekaClassifier.N71cd966c14(i);
        }
        return p;
    }
    static double N48c439892(Object []i) {
        double p = Double.NaN;
        if (i[0] == null) {
            p = 1;
        } else if (((Double) i[0]).doubleValue() <= 178.849911) {
            p = WekaClassifier.N3f0e52823(i);
        } else if (((Double) i[0]).doubleValue() > 178.849911) {
            p = WekaClassifier.N3b5b4ecb11(i);
        }
        return p;
    }
    static double N3f0e52823(Object []i) {
        double p = Double.NaN;
        if (i[7] == null) {
            p = 1;
        } else if (((Double) i[7]).doubleValue() <= 5.087455) {
            p = 1;
        } else if (((Double) i[7]).doubleValue() > 5.087455) {
            p = WekaClassifier.N2303fc3a4(i);
        }
        return p;
    }
    static double N2303fc3a4(Object []i) {
        double p = Double.NaN;
        if (i[31] == null) {
            p = 0;
        } else if (((Double) i[31]).doubleValue() <= 1.986846) {
            p = WekaClassifier.N420a1d1b5(i);
        } else if (((Double) i[31]).doubleValue() > 1.986846) {
            p = WekaClassifier.N38ec349010(i);
        }
        return p;
    }
    static double N420a1d1b5(Object []i) {
        double p = Double.NaN;
        if (i[6] == null) {
            p = 1;
        } else if (((Double) i[6]).doubleValue() <= 8.79013) {
            p = WekaClassifier.N2beb7ca26(i);
        } else if (((Double) i[6]).doubleValue() > 8.79013) {
            p = 0;
        }
        return p;
    }
    static double N2beb7ca26(Object []i) {
        double p = Double.NaN;
        if (i[4] == null) {
            p = 1;
        } else if (((Double) i[4]).doubleValue() <= 15.991761) {
            p = WekaClassifier.N663ca11f7(i);
        } else if (((Double) i[4]).doubleValue() > 15.991761) {
            p = 0;
        }
        return p;
    }
    static double N663ca11f7(Object []i) {
        double p = Double.NaN;
        if (i[24] == null) {
            p = 0;
        } else if (((Double) i[24]).doubleValue() <= 1.514592) {
            p = WekaClassifier.N5ae263ce8(i);
        } else if (((Double) i[24]).doubleValue() > 1.514592) {
            p = 1;
        }
        return p;
    }
    static double N5ae263ce8(Object []i) {
        double p = Double.NaN;
        if (i[13] == null) {
            p = 1;
        } else if (((Double) i[13]).doubleValue() <= 2.057052) {
            p = WekaClassifier.N48924f4c9(i);
        } else if (((Double) i[13]).doubleValue() > 2.057052) {
            p = 0;
        }
        return p;
    }
    static double N48924f4c9(Object []i) {
        double p = Double.NaN;
        if (i[3] == null) {
            p = 1;
        } else if (((Double) i[3]).doubleValue() <= 15.238512) {
            p = 1;
        } else if (((Double) i[3]).doubleValue() > 15.238512) {
            p = 0;
        }
        return p;
    }
    static double N38ec349010(Object []i) {
        double p = Double.NaN;
        if (i[20] == null) {
            p = 0;
        } else if (((Double) i[20]).doubleValue() <= 1.697702) {
            p = 0;
        } else if (((Double) i[20]).doubleValue() > 1.697702) {
            p = 1;
        }
        return p;
    }
    static double N3b5b4ecb11(Object []i) {
        double p = Double.NaN;
        if (i[10] == null) {
            p = 1;
        } else if (((Double) i[10]).doubleValue() <= 9.475996) {
            p = 1;
        } else if (((Double) i[10]).doubleValue() > 9.475996) {
            p = WekaClassifier.N72eea67d12(i);
        }
        return p;
    }
    static double N72eea67d12(Object []i) {
        double p = Double.NaN;
        if (i[0] == null) {
            p = 1;
        } else if (((Double) i[0]).doubleValue() <= 347.415338) {
            p = WekaClassifier.N285c6f4313(i);
        } else if (((Double) i[0]).doubleValue() > 347.415338) {
            p = 2;
        }
        return p;
    }
    static double N285c6f4313(Object []i) {
        double p = Double.NaN;
        if (i[32] == null) {
            p = 0;
        } else if (((Double) i[32]).doubleValue() <= 1.512221) {
            p = 0;
        } else if (((Double) i[32]).doubleValue() > 1.512221) {
            p = 1;
        }
        return p;
    }
    static double N71cd966c14(Object []i) {
        double p = Double.NaN;
        if (i[0] == null) {
            p = 2;
        } else if (((Double) i[0]).doubleValue() <= 737.324184) {
            p = WekaClassifier.N1001b38815(i);
        } else if (((Double) i[0]).doubleValue() > 737.324184) {
            p = 2;
        }
        return p;
    }
    static double N1001b38815(Object []i) {
        double p = Double.NaN;
        if (i[6] == null) {
            p = 2;
        } else if (((Double) i[6]).doubleValue() <= 11.347626) {
            p = WekaClassifier.N17db22bb16(i);
        } else if (((Double) i[6]).doubleValue() > 11.347626) {
            p = 2;
        }
        return p;
    }
    static double N17db22bb16(Object []i) {
        double p = Double.NaN;
        if (i[2] == null) {
            p = 2;
        } else if (((Double) i[2]).doubleValue() <= 42.541848) {
            p = 2;
        } else if (((Double) i[2]).doubleValue() > 42.541848) {
            p = 1;
        }
        return p;
    }
}
