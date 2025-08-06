package com.example.mycomposetoy.presentation.main

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.navigation3.runtime.NavKey

class TopLevelBackStack<T : NavKey>(startKey: T) {

    // 탭 별로 백스택을 따로 저장하는 맵 = Home에서 detail로 이동 시 여기에서 사용
    private var topLevelBackStacks: HashMap<T, SnapshotStateList<T>> = hashMapOf(
        startKey to mutableStateListOf(startKey)
    )

    // 현재 탭 정보 , 외부에서 읽은 수는 있지만 수정은 못하게
    var topLevelKey by mutableStateOf(startKey) // currentTab
        private set

    // 전체 backStack 을 하나로 합쳐서 보여줌 / display에 넌ㅁ겨서 실제 ui 전환에 사용
    val backStack = mutableStateListOf(startKey)


    // 텝 전환 시 backstack을 ui가 볼 수 있는 형태로 구성
    // 홈 탭이 아닌 경우 항상 홈 탭까지의 백스택을 먼저 넣고 그 후 현재 탭 스택을 추가하여
    // 전체 네비게이션 경로처럼 보이도록 함 따라서 반드시 홈이 최하위에 존재하도록하고 나머지는 현재 route만 top에 위치하도록
    private fun updateBackStack() {
        /*backStack.clear()
        val currentStack = topLevelBackStacks[topLevelKey] ?: emptyList()
        Log.d("TAG", "updateBackStack: $currentStack")
        // 첫 시작 점 유지인데 ui 상에서 막고 있으니 제거해도될듯
        if (topLevelKey == startKey) {
            backStack.addAll(currentStack)
            Log.d("TAG", "updateBackStack ts: $backStack")
        } else {
            val startStack = topLevelBackStacks[startKey] ?: emptyList()
            backStack.addAll(startStack + currentStack)
            Log.d("TAG", "updateBackStack else: $backStack")
        }*/


        val currentStack2 = topLevelKey
        Log.d("TAG", "updateBackStack: $currentStack2")
        if (backStack.contains(currentStack2)) {
            backStack.remove(currentStack2)
        }
        backStack.add(currentStack2)
        Log.d("TAG", "updateBackStack: $backStack")
    }

    // 탭 전환 시 해당 탭이 최초 접근이라면 초기값만 담은 스택 생성
    // 현재 탭 변경하고 back stack 재구성
    fun switchTopLevel(key: T) {
        if (topLevelBackStacks[key] == null) {
            topLevelBackStacks[key] = mutableStateListOf(key)
        }
        Log.d("TAG", "switchTopLevel: $key")
        topLevelKey = key
        updateBackStack()
    }

    // 탭 내에서 상세화면 진입시 -> 메인 홈->홈에서 아이템으로 이동
    fun add(key: T) {
        topLevelBackStacks[topLevelKey]?.add(key)
        updateBackStack()
    }

    // 탭 내에서 뒤로가기 시 -> 메인 홈->홈에서 뒤로가기
    fun removeLast() {
        /*val currentStack = topLevelBackStacks[topLevelKey] ?: return
        Log.e("TAG", "removeLast: $currentStack")
        if (currentStack.size > 1) {
            currentStack.removeLastOrNull()
            Log.e("TAG", "removeLast size1: $currentStack")
        } else if (topLevelKey != startKey) { // 맨 처음 시작 키를 제외한 모든 키에서 뒤로가기
            topLevelKey = startKey
            Log.e("TAG", "removeLast size2: $currentStack")
        } else {
            Log.e("TAG", "removeLast size3: $currentStack")
        }*/

        val currentStack = topLevelBackStacks[topLevelKey] ?: return
        if (currentStack.size > 1) {
            currentStack.removeLastOrNull()
        } else {
            // 현재 탭 스택에 더 이상 pop 할 수 없으면 전체 backStack 에서 제거
            if (backStack.size > 1) {
                backStack.removeLastOrNull()
                topLevelKey = backStack.last()
            }
        }
        updateBackStack()
    }

    // 현재 탭 스택을 다른 키들로 교체
    // 특별한 라우팅 시나리오에서 사용 -> 로그인 => 특정 경로로 이동
    // vararg는 여러개의 route를 넘겨서 현재 탭의 백스택을 리스트로 교체할 수 있게
    fun replaceStack(vararg keys: T) {
        topLevelBackStacks[topLevelKey] = mutableStateListOf(*keys)
        updateBackStack()
    }
}