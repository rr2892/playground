#include <iostream>
#include <cstdio>
#include <cmath>
#include <cstring>
#include <algorithm>
#include <string>
#include <vector>
#include <stack>
#include <queue>
#include <set>
#include <map>
#include <sstream>
#include <complex>
#include <ctime>
#include <cassert>
#include <functional>

using namespace std;

typedef long long ll;
typedef vector<int> VI;
typedef pair<int,int> PII;

#define REP(i,s,t) for(int i=(s);i<(t);i++)
#define FILL(x,v) memset(x,v,sizeof(x))

const int INF = (int)1E9;
#define MAXN 35

char g[MAXN][MAXN], g2[MAXN][MAXN], gb[MAXN][MAXN], ng[MAXN][MAXN];
void rotateClockwise(int &n, int &m, char g[][MAXN], char ng[][MAXN], bool writeBack=false) {
  REP(i,0,m) REP(j,0,n) ng[i][j] = g[n-1-j][i];
  if (writeBack) {
    swap(n, m);
    REP(i,0,n) REP(j,0,m) g[i][j] = ng[i][j];
  }
}
void flipHorizontal(int &n, int &m, char g[][MAXN], char ng[][MAXN], bool writeBack=false) {
  if (writeBack) REP(i,0,n) REP(j,0,m/2) swap(g[i][j], g[i][m-1-j]);
  else REP(i,0,n) REP(j,0,m) ng[i][j] = g[i][m-1-j];
}
int main() {
  int n, m, N, M;
  cin >> n >> m;
  REP(i,0,n) scanf("%s", g[i]);
  cin >> N >> M;
  REP(i,0,N) scanf("%s", g2[i]);
  int ans = 0;
  REP(t,0,2) {
    REP(r,0,4) {
      REP(sx,-n+1,N) {
        REP(sy,-m+1,M) {
          int sol = 0;
          REP(i,0,n) {
            REP(j,0,m) {
              int x = sx + i, y = sy + j;
              if (x < 0 || y < 0 || x >= N || y >= M) continue;
              if (g[i][j] == '#' && g2[x][y] == '#') sol++;
            }
          }
          ans = max(ans, sol);
        }
      }
      rotateClockwise(n, m, g, ng, true);
    }
    flipHorizontal(n, m, g, ng, true);
  }
  cout << ans << endl;
  return 0;
}
