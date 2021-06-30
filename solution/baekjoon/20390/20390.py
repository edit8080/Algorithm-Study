# 1. 간선의 개수가 V^2 에 가까우므로(완전 그래프) 프림 알고리즘을 활용한다.
# 2. 확인점 집합을 구성할 때마다 가중치를 계산해 dist 배열에 저장하여 활용한다.
#
# 주의) - 간선들의 가중치를 계산할 때 i<j 조건을 유의한다.
#      - 메모리 초과가 발생할 수 있으므로 2차원 배열로 edge를 미리 설정하지 않는다.
# 시간복잡도 = O(V^2)

n = int(input())
a, b, c, d = map(int, input().split(" "))

dist = [2 ** 63 - 1 for _ in range(n)]
selected = [False for _ in range(n)]

x = list(map(int, input().split(" ")))


def prim():
    min_val = 0
    dist[0] = 0

    for _ in range(0, n):
        min_dist = 2**63-1
        now = -1

        for j in range(0, n):
            if not(selected[j]) and min_dist > dist[j]:
                now = j
                min_dist = dist[j]

        selected[now] = True
        min_val += min_dist

        # 확인점들과의 거리를 업데이트한다.
        for k in range(0, n):
            if now == k:
                continue
            first = now if now < k else k
            second = k if now < k else now
            weight = (x[first] * a + x[second] * b) % c ^ d
            dist[k] = min(dist[k], weight)

    return min_val


if __name__ == "__main__":
    print(prim())
