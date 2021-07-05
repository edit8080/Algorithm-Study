/*
* 1. 명령어를 입력받고 입력값에 따라 스택의 명령을 수행한다.
* 2. 에러처리와 부호처리에 주의한다.
*/

#include <iostream>
#include <stack>
#include <vector>
#include <cmath>

using namespace std;

stack<long long> s;

// 절댓값 10^9 내에 있는지 확인
bool in_range(long long x) {
	return abs(x) <= 1000000000 ? true : false;
}
// 값을 뽑을 수 있는지 확인
bool cannot_get_value() {
	return s.empty() ? true : false;
}

bool stack_trigger(string cmd, vector<int>& num, int num_idx) {
	long long val1, val2;
	long long ret;

	// num값 삽입
	if (!cmd.compare("NUM")) {
		s.push(num[num_idx]);
	}

	// 가장 위의 값 제거
	else if (!cmd.compare("POP")) {
		if (cannot_get_value()) return false;
		s.pop();
	}

	// 가장 위의 값 부호 변경
	else if (!cmd.compare("INV")) {
		if (cannot_get_value()) return false;
		val1 = s.top();
		s.pop();
		s.push(val1 * (-1));
	}

	// 가장 위의 값 복사
	else if (!cmd.compare("DUP")) {
		if (cannot_get_value()) return false;
		s.push(s.top());
	}

	// 첫번째, 두번째값 교환
	else if (!cmd.compare("SWP")) {
		if (cannot_get_value()) return false;
		val1 = s.top(); s.pop();

		if (cannot_get_value()) return false;
		val2 = s.top(); s.pop();

		s.push(val1);
		s.push(val2);
	}

	// 두번째값 + 첫번째값
	else if (!cmd.compare("ADD")) {
		if (cannot_get_value()) return false;
		val1 = s.top(); s.pop();

		if (cannot_get_value()) return false;
		val2 = s.top(); s.pop();

		ret = val2 + val1;
		if (!in_range(ret)) return false;

		s.push(ret);
	}

	// 두번째값 - 첫번째값
	else if (!cmd.compare("SUB")) {
		if (cannot_get_value()) return false;
		val1 = s.top(); s.pop();

		if (cannot_get_value()) return false;
		val2 = s.top(); s.pop();

		ret = val2 - val1;
		if (!in_range(ret)) return false;

		s.push(val2 - val1);
	}

	// 두번째값 * 첫번째값
	else if (!cmd.compare("MUL")) {
		if (cannot_get_value()) return false;
		val1 = s.top(); s.pop();

		if (cannot_get_value()) return false;
		val2 = s.top(); s.pop();

		ret = val2 * val1;
		if (!in_range(ret)) return false;

		s.push(val1 * val2);
	}

	// 두번째값 / 첫번째값
	else if (!cmd.compare("DIV")) {
		int cnt = 0;
		if (cannot_get_value()) return false;
		val1 = s.top(); s.pop();

		if (cannot_get_value()) return false;
		val2 = s.top(); s.pop();

		if (val1 == 0) return false;
		ret = abs(val2) / abs(val1);

		if (!in_range(ret)) return false;
		((val1 < 0) + (val2 < 0)) % 2 ? s.push(ret * (-1)) : s.push(ret);
	}

	// 두번째값 % 첫번째값
	else if (!cmd.compare("MOD")) {
		if (cannot_get_value()) return false;
		val1 = s.top(); s.pop();

		if (cannot_get_value()) return false;
		val2 = s.top(); s.pop();

		if (val1 == 0) return false;
		ret = abs(val2) % abs(val1);

		if (!in_range(ret)) return false;
		val2 < 0 ? s.push(ret * (-1)) : s.push(ret);
	}
	return true;
}
void program() {
	string cmd;
	int x;
	int input_size;
	int input;

	while (true) {
		vector<string> commands;
		vector<int> num;

		// 프로그램 입력
		while (true) {
			cin >> cmd;
			if (!cmd.compare("END"))
				break;
			commands.push_back(cmd);

			if (!cmd.compare("NUM")) {
				cin >> x;
				num.push_back(x);
			}
			if (!cmd.compare("QUIT"))
				return;
		}

		// 입력과 프로그램 명령어 처리
		cin >> input_size;
		while (input_size--) {

			// 스택 초기화
			while (!s.empty())
				s.pop();

			bool error = false;
			int num_idx = 0;

			cin >> input;
			s.push(input);

			// 프로그램 수행
			for (int i = 0; i < (int)commands.size(); i++) {
				if (!stack_trigger(commands[i], num, num_idx))
					error = true;
				if (!commands[i].compare("NUM"))
					num_idx += 1;
			}
			// 에러 체크
			if (error || s.size() != 1)
				cout << "ERROR" << '\n';
			else
				cout << s.top() << '\n';
		}
		cout << '\n';
	}
}

int main() {
	program();
}