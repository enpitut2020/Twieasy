package com.example.twieasy

import androidx.lifecycle.ViewModel

data class Subject(
    var name: String,
    var info: String,
    var easiness: Int,
    var reviews: MutableCollection<String>
)//構造体みたいなクラス


private var reviews1: MutableCollection<String> = mutableListOf(
    "楽単!",
    "落単!",
    "普通!",
    "Easy!",
    "楽単!",
    "落単!",
    "楽単!",
    "落単!",
    "普通!",
    "Easy!",
    "楽単!",
    "落単!",
    "楽単!",
    "落単!",
    "普通!",
    "Easy!",
    "楽単!",
    "落単!",
    "楽単!",
    "落単!",
    "普通!",
    "Easy!",
    "楽単!",
    "落単!",
    "楽単!",
    "落単!",
    "普通!",
    "Easy!",
    "楽単!",
    "落単!",
    "楽単!",
    "落単!",
    "普通!",
    "Easy!",
    "楽単!",
    "落単!"
)
private var reviews2: MutableCollection<String> = mutableListOf(
    "楽単!(人工知能)",
    "落単!(人工知能)",
    "普通!(人工知能)",
    "Easy!(人工知能)"
)
private var reviews3: MutableCollection<String> = mutableListOf(
    "楽単!(人工知能)",
    "落単!(人工知能)",
    "普通!(人工知能)",
    "Easy!(人工知能)"
)
private var reviews4: MutableCollection<String> = mutableListOf(
    "楽単!(人工知能)",
    "落単!(人工知能)",
    "普通!(人工知能)",
    "Easy!(人工知能)"
)
private var reviews5: MutableCollection<String> = mutableListOf(
    "楽単!(人工知能)",
    "落単!(人工知能)",
    "普通!(人工知能)",
    "Easy!(人工知能)"
)
private var reviews6: MutableCollection<String> = mutableListOf(
    "楽単!(人工知能)",
    "落単!(人工知能)",
    "普通!(人工知能)",
    "Easy!(人工知能)"
)
private var reviews7: MutableCollection<String> = mutableListOf(
    "楽単!(人工知能)",
    "落単!(人工知能)",
    "普通!(人工知能)",
    "Easy!(人工知能)"
)
private var reviews8: MutableCollection<String> = mutableListOf(
    "楽単!(人工知能)",
    "落単!(人工知能)",
    "普通!(人工知能)",
    "Easy!(人工知能)"
)


class SubjectViewModel: ViewModel() {

    var reviewList = mutableListOf(
        reviews1,
        reviews2,
        reviews3,
        reviews4,
        reviews5,
        reviews6,
        reviews7,
        reviews8
    )

    var subjectsInfo =  mutableListOf(
        Subject(
            "知能情報メディア実験B",
            "開講日時　秋ABC 水3,4 金5,6\n授業形態 オンライン オンデマンド 同時双方向 対面\n評価方法　レポートn割 出席m割\n単位数 3",
            40,
            reviewList[0]
        ),
        Subject(
            "人工知能",
            "開講日時　秋AB 火3,4\n授業形態 オンライン オンデマンド\n評価方法　レポートn割 出席m割\n単位数 2",
            20,
            reviewList[1]
        ),
        Subject(
            "分散システム",
            "開講日時　秋AB 月3\n授業形態 対面 オンデマンド\n評価方法　レポートn割 出席m割\n単位数 1",
            20,
            reviewList[2]
        ),
        Subject(
            "画像メディア工学",
            "開講日時　秋AB 火5,6\n授業形態 オンデマンド\n評価方法　レポートn割 出席m割\n単位数 2",
            20,
            reviewList[3]
        ),
        Subject(
            "視覚情報科学",
            "開講日時　秋AB 木1,2\n授業形態 オンデマンド\n評価方法　レポートn割 出席m割\n単位数 2",
            60,
            reviewList[4]
        ),
        Subject(
            "パターン認識",
            "開講日時　秋AB 木3,4\n授業形態 オンライン オンデマンド\n評価方法　レポートn割 出席m割\n単位数 2",
            40,
            reviewList[5]
        ),
        Subject(
            "数理アルゴリズムとシミュレーション",
            "開講日時　秋AB 金1,2\n授業形態 オンデマンド\n評価方法　レポートn割 出席m割\n単位数 2",
            30,
            reviewList[6]
        ),
        Subject(
            "データベース概論",
            "開講日時　秋AB 金3,4\n授業形態 オンデマンド\n評価方法　レポートn割 出席m割\n単位数 2",
            40,
            reviewList[7]
        )
    )


    var subjectNumber = mutableListOf<String>()

    // KDBからとってきた生データ
    var kdbRawData = mutableListOf<String>()


    var subjects = mutableListOf<Subject>()

    fun clear(){
        subjectsInfo.clear()
    }



    fun size(): Int{
        return subjectsInfo.size
    }
}