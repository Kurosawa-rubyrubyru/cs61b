import edu.princeton.cs.algs4.Picture;

import java.awt.Color;

public class SeamCarver {

    private int width;
    private int height;
    private Picture picture;
    private int[][] energy;

    public SeamCarver(Picture picture) {
        width = picture.width();
        height = picture.height();
        energy = new int[width][height];
        this.picture = new Picture(picture);
        Color color1, color2, color3, color4;
        for (int i = 0; i < width; i += 1) {
            for (int j = 0; j < height; j += 1) {
                if (i > 0) {
                    color1 = picture.get(i - 1, j);
                } else {
                    color1 = picture.get(width - 1, j);
                }
                color2 = picture.get((i + 1) % width, j);
                if (j > 0) {
                    color3 = picture.get(i, j - 1);
                } else {
                    color3 = picture.get(i, height - 1);
                }
                color4 = picture.get(i, (j + 1) % height);
                energy[i][j] += Math.pow((color1.getRed() - color2.getRed()), 2);
                energy[i][j] += Math.pow((color1.getGreen() - color2.getGreen()), 2);
                energy[i][j] += Math.pow((color1.getBlue() - color2.getBlue()), 2);
                energy[i][j] += Math.pow((color3.getRed() - color4.getRed()), 2);
                energy[i][j] += Math.pow((color3.getGreen() - color4.getGreen()), 2);
                energy[i][j] += Math.pow((color3.getBlue() - color4.getBlue()), 2);
            }
        }
    }

    public Picture picture() {
        Picture newpicture = new Picture(picture);
        return newpicture;
    }                       // current picture

    public int width() {
        return width;
    }                        // width of current picture

    public int height() {
        return height;
    }                   // height of current picture

    public double energy(int x, int y) {
        return energy[x][y];
    }            // energy of pixel at column x and row y

    public int[] findHorizontalSeam() {
        int[][] newpicture = new int[height][width];
        int[][] oldpicture = new int[width][height];
        for (int j = 0; j < height; j += 1) {
            for (int i = 0; i < width; i += 1) {
                newpicture[j][i] = energy[i][j];
                oldpicture[i][j] = energy[i][j];
            }
        }
        this.energy = newpicture;
        int trans = height;
        height = width;
        width = trans;

        int[] ans = new int[height];
        int[] mymin = new int[2];
        int[][] pre = new int[width][height];
        int[][] sum = new int[width][height];
        for (int j = 0; j < height; j += 1) {
            for (int i = 0; i < width; i += 1) {
                if (j == 0) {
                    sum[i][j] = energy[i][j];
                    pre[i][j] = i;

                } else {
                    if (width == 1) {
                        mymin = myMin(19999999, sum[i][j - 1], 199999999);
                    } else if (i == width - 1) {
                        mymin = myMin(sum[i - 1][j - 1], sum[i][j - 1], 199999999);
                    } else if (i == 0) {
                        mymin = myMin(199999999, sum[i][j - 1], sum[i + 1][j - 1]);
                    } else {
                        mymin = myMin(sum[i - 1][j - 1], sum[i][j - 1], sum[i + 1][j - 1]);
                    }
                    sum[i][j] = sum[i - 1 + mymin[0]][j - 1] + energy[i][j];
                    pre[i][j] = i - 1 + mymin[0];
                }
            }
        }
        int min = 199999999;
        int anspos = 0;
        for (int i = 0; i < width; i += 1) {
            if (sum[i][height - 1] < min) {
                min = sum[i][height - 1];
                anspos = i;
            }
        }
        for (int i = height - 1; i >= 0; i -= 1) {
            ans[i] = anspos;
            anspos = pre[anspos][i];
        }
        energy = oldpicture;
        trans = height;
        height = width;
        width = trans;
        return ans;
    }          // sequence of indices for horizontal seam

    public int[] findVerticalSeam() {
        int[] ans = new int[height];
        int[] mymin = new int[2];
        int[][] pre = new int[width][height];
        int[][] sum = new int[width][height];
        for (int j = 0; j < height; j += 1) {
            for (int i = 0; i < width; i += 1) {
                if (j == 0) {
                    sum[i][j] = energy[i][j];
                    pre[i][j] = i;

                } else {
                    if (width == 1) {
                        mymin = myMin(19999999, sum[i][j - 1], 199999999);
                    } else if (i == width - 1) {
                        mymin = myMin(sum[i - 1][j - 1], sum[i][j - 1], 199999999);
                    } else if (i == 0) {
                        mymin = myMin(199999999, sum[i][j - 1], sum[i + 1][j - 1]);
                    } else {
                        mymin = myMin(sum[i - 1][j - 1], sum[i][j - 1], sum[i + 1][j - 1]);
                    }
                    sum[i][j] = sum[i - 1 + mymin[0]][j - 1] + energy[i][j];
                    pre[i][j] = i - 1 + mymin[0];

                }
            }
        }
        int min = 199999999;
        int anspos = 0;
        for (int i = 0; i < width; i += 1) {
            if (sum[i][height - 1] < min) {
                min = sum[i][height - 1];
                anspos = i;
            }
        }
        for (int i = height - 1; i >= 0; i -= 1) {
            ans[i] = anspos;
            anspos = pre[anspos][i];
        }
        return ans;
    }


    public void removeHorizontalSeam(int[] seam) {
        picture = SeamRemover.removeHorizontalSeam(picture, seam);
        width = picture.width();
        height = picture.height();
        energy = new int[width][height];
        Color color1, color2, color3, color4;
        for (int i = 0; i < width; i += 1) {
            for (int j = 0; j < height; j += 1) {
                if (i > 0) {
                    color1 = picture.get(i - 1, j);
                } else {
                    color1 = picture.get(width - 1, j);
                }
                color2 = picture.get((i + 1) % width, j);
                if (j > 0) {
                    color3 = picture.get(i, j - 1);
                } else {
                    color3 = picture.get(i, height - 1);
                }
                color4 = picture.get(i, (j + 1) % height);
                energy[i][j] += Math.pow((color1.getRed() - color2.getRed()), 2);
                energy[i][j] += Math.pow((color1.getGreen() - color2.getGreen()), 2);
                energy[i][j] += Math.pow((color1.getBlue() - color2.getBlue()), 2);
                energy[i][j] += Math.pow((color3.getRed() - color4.getRed()), 2);
                energy[i][j] += Math.pow((color3.getGreen() - color4.getGreen()), 2);
                energy[i][j] += Math.pow((color3.getBlue() - color4.getBlue()), 2);
            }
        }
        //改数值
    }   // remove horizontal seam from picture

    public void removeVerticalSeam(int[] seam) {
        picture = SeamRemover.removeVerticalSeam(picture, seam);
        width = picture.width();
        height = picture.height();
        energy = new int[width][height];
        Color color1, color2, color3, color4;
        for (int i = 0; i < width; i += 1) {
            for (int j = 0; j < height; j += 1) {
                if (i > 0) {
                    color1 = picture.get(i - 1, j);
                } else {
                    color1 = picture.get(width - 1, j);
                }
                color2 = picture.get((i + 1) % width, j);
                if (j > 0) {
                    color3 = picture.get(i, j - 1);
                } else {
                    color3 = picture.get(i, height - 1);
                }
                color4 = picture.get(i, (j + 1) % height);
                energy[i][j] += Math.pow((color1.getRed() - color2.getRed()), 2);
                energy[i][j] += Math.pow((color1.getGreen() - color2.getGreen()), 2);
                energy[i][j] += Math.pow((color1.getBlue() - color2.getBlue()), 2);
                energy[i][j] += Math.pow((color3.getRed() - color4.getRed()), 2);
                energy[i][j] += Math.pow((color3.getGreen() - color4.getGreen()), 2);
                energy[i][j] += Math.pow((color3.getBlue() - color4.getBlue()), 2);
            }
        }
    }  // remove vertical seam from picture

    private int[] myMin(int a, int b, int c) {
        int[] ans = new int[2];
        if (a <= b && a <= c) {
            ans[0] = 0;
            ans[1] = a;
        } else if (b <= a && b <= c) {
            ans[0] = 1;
            ans[1] = b;
        } else {
            ans[0] = 2;
            ans[1] = c;
        }
        return ans;
    }

}
