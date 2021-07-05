/*
* 1. ��ɾ �Է¹ް� �Է°��� ���� ������ ����� �����Ѵ�.
* 2. ����ó���� ��ȣó���� �����Ѵ�.
*/

#include <iostream>
#include <stack>
#include <vector>
#include <cmath>

using namespace std;

stack<long long> s;

// ���� 10^9 ���� �ִ��� Ȯ��
bool in_range(long long x) {
	return abs(x) <= 1000000000 ? true : false;
}
// ���� ���� �� �ִ��� Ȯ��
bool cannot_get_value() {
	return s.empty() ? true : false;
}

bool stack_trigger(string cmd, vector<int>& num, int num_idx) {
	long long val1, val2;
	long long ret;

	// num�� ����
	if (!cmd.compare("NUM")) {
		s.push(num[num_idx]);
	}

	// ���� ���� �� ����
	else if (!cmd.compare("POP")) {
		if (cannot_get_value()) return false;
		s.pop();
	}

	// ���� ���� �� ��ȣ ����
	else if (!cmd.compare("INV")) {
		if (cannot_get_value()) return false;
		val1 = s.top();
		s.pop();
		s.push(val1 * (-1));
	}

	// ���� ���� �� ����
	else if (!cmd.compare("DUP")) {
		if (cannot_get_value()) return false;
		s.push(s.top());
	}

	// ù��°, �ι�°�� ��ȯ
	else if (!cmd.compare("SWP")) {
		if (cannot_get_value()) return false;
		val1 = s.top(); s.pop();

		if (cannot_get_value()) return false;
		val2 = s.top(); s.pop();

		s.push(val1);
		s.push(val2);
	}

	// �ι�°�� + ù��°��
	else if (!cmd.compare("ADD")) {
		if (cannot_get_value()) return false;
		val1 = s.top(); s.pop();

		if (cannot_get_value()) return false;
		val2 = s.top(); s.pop();

		ret = val2 + val1;
		if (!in_range(ret)) return false;

		s.push(ret);
	}

	// �ι�°�� - ù��°��
	else if (!cmd.compare("SUB")) {
		if (cannot_get_value()) return false;
		val1 = s.top(); s.pop();

		if (cannot_get_value()) return false;
		val2 = s.top(); s.pop();

		ret = val2 - val1;
		if (!in_range(ret)) return false;

		s.push(val2 - val1);
	}

	// �ι�°�� * ù��°��
	else if (!cmd.compare("MUL")) {
		if (cannot_get_value()) return false;
		val1 = s.top(); s.pop();

		if (cannot_get_value()) return false;
		val2 = s.top(); s.pop();

		ret = val2 * val1;
		if (!in_range(ret)) return false;

		s.push(val1 * val2);
	}

	// �ι�°�� / ù��°��
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

	// �ι�°�� % ù��°��
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

		// ���α׷� �Է�
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

		// �Է°� ���α׷� ��ɾ� ó��
		cin >> input_size;
		while (input_size--) {

			// ���� �ʱ�ȭ
			while (!s.empty())
				s.pop();

			bool error = false;
			int num_idx = 0;

			cin >> input;
			s.push(input);

			// ���α׷� ����
			for (int i = 0; i < (int)commands.size(); i++) {
				if (!stack_trigger(commands[i], num, num_idx))
					error = true;
				if (!commands[i].compare("NUM"))
					num_idx += 1;
			}
			// ���� üũ
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