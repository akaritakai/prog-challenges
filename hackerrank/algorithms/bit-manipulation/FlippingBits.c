#include <stdio.h>
#include <string.h>
#include <math.h>
#include <stdlib.h>

// https://www.hackerrank.com/challenges/flipping-bits/
int main() {
    int t, i;
    unsigned int v;
    scanf("%d", &t);
    for (i = 0; i < t; i++) {
        scanf("%d", &v);
        printf("%u\n", (v ^ 0xFFFFFFFF));
    }
    return 0;
}
