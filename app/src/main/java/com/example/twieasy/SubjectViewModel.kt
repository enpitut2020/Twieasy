package com.example.twieasy

import androidx.lifecycle.ViewModel

data class Subject(
    var name: String,
    var info: String,
    var easiness: Int,
    var reviews: MutableCollection<String>
)//構造体みたいなクラス

enum class Department {
    COINS, MAST, KLIS
}

private var reviews0: MutableCollection<String> = mutableListOf()
private var reviews1: MutableCollection<String> = mutableListOf()
private var reviews2: MutableCollection<String> = mutableListOf()
private var reviews3: MutableCollection<String> = mutableListOf()
private var reviews4: MutableCollection<String> = mutableListOf()
private var reviews5: MutableCollection<String> = mutableListOf()
private var reviews6: MutableCollection<String> = mutableListOf("楽単!", "落単!", "普通!", "Easy!")
private var reviews7: MutableCollection<String> = mutableListOf("楽単!", "落単!", "普通!", "Easy!")
private var reviews8: MutableCollection<String> = mutableListOf("楽単!", "落単!", "普通!", "Easy!")
private var reviews9: MutableCollection<String> = mutableListOf("楽単!", "落単!", "普通!", "Easy!")
private var reviews10: MutableCollection<String> = mutableListOf("楽単!", "落単!", "普通!", "Easy!")
private var reviews11: MutableCollection<String> = mutableListOf("楽単!", "落単!", "普通!", "Easy!")
private var reviews12: MutableCollection<String> = mutableListOf("楽単!", "落単!", "普通!", "Easy!")
private var reviews13: MutableCollection<String> = mutableListOf("楽単!", "落単!", "普通!", "Easy!")
private var reviews14: MutableCollection<String> = mutableListOf("楽単!", "落単!", "普通!", "Easy!")
private var reviews15: MutableCollection<String> = mutableListOf("楽単!", "落単!", "普通!", "Easy!")
private var reviews16: MutableCollection<String> = mutableListOf("楽単!", "落単!", "普通!", "Easy!")
private var reviews17: MutableCollection<String> = mutableListOf("楽単!", "落単!", "普通!", "Easy!")
private var reviews18: MutableCollection<String> = mutableListOf("楽単!", "落単!", "普通!", "Easy!")
private var reviews19: MutableCollection<String> = mutableListOf("楽単!", "落単!", "普通!", "Easy!")
private var reviews20: MutableCollection<String> = mutableListOf("楽単!", "落単!", "普通!", "Easy!")
private var reviews21: MutableCollection<String> = mutableListOf("楽単!", "落単!", "普通!", "Easy!")
private var reviews22: MutableCollection<String> = mutableListOf("楽単!", "落単!", "普通!", "Easy!")
private var reviews23: MutableCollection<String> = mutableListOf("楽単!", "落単!", "普通!", "Easy!")
private var reviews24: MutableCollection<String> = mutableListOf("楽単!", "落単!", "普通!", "Easy!")
private var reviews25: MutableCollection<String> = mutableListOf("楽単!", "落単!", "普通!", "Easy!")
private var reviews26: MutableCollection<String> = mutableListOf("楽単!", "落単!", "普通!", "Easy!")
private var reviews27: MutableCollection<String> = mutableListOf("楽単!", "落単!", "普通!", "Easy!")
private var reviews28: MutableCollection<String> = mutableListOf("楽単!", "落単!", "普通!", "Easy!")
private var reviews29: MutableCollection<String> = mutableListOf("楽単!", "落単!", "普通!", "Easy!")
private var reviews30: MutableCollection<String> = mutableListOf("楽単!", "落単!", "普通!", "Easy!")
private var reviews31: MutableCollection<String> = mutableListOf("楽単!", "落単!", "普通!", "Easy!")
private var reviews32: MutableCollection<String> = mutableListOf("楽単!", "落単!", "普通!", "Easy!")
private var reviews33: MutableCollection<String> = mutableListOf("楽単!", "落単!", "普通!", "Easy!")
private var reviews34: MutableCollection<String> = mutableListOf("楽単!", "落単!", "普通!", "Easy!")
private var reviews35: MutableCollection<String> = mutableListOf("楽単!", "落単!", "普通!", "Easy!")
private var reviews36: MutableCollection<String> = mutableListOf("楽単!", "落単!", "普通!", "Easy!")
private var reviews37: MutableCollection<String> = mutableListOf("楽単!", "落単!", "普通!", "Easy!")
private var reviews38: MutableCollection<String> = mutableListOf("楽単!", "落単!", "普通!", "Easy!")
private var reviews39: MutableCollection<String> = mutableListOf("楽単!", "落単!", "普通!", "Easy!")
private var reviews40: MutableCollection<String> = mutableListOf("楽単!", "落単!", "普通!", "Easy!")
private var reviews41: MutableCollection<String> = mutableListOf("楽単!", "落単!", "普通!", "Easy!")
private var reviews42: MutableCollection<String> = mutableListOf("楽単!", "落単!", "普通!", "Easy!")
private var reviews43: MutableCollection<String> = mutableListOf("楽単!", "落単!", "普通!", "Easy!")
private var reviews44: MutableCollection<String> = mutableListOf("楽単!", "落単!", "普通!", "Easy!")
private var reviews45: MutableCollection<String> = mutableListOf("楽単!", "落単!", "普通!", "Easy!")
private var reviews46: MutableCollection<String> = mutableListOf("楽単!", "落単!", "普通!", "Easy!")


class SubjectViewModel: ViewModel() {

    var loaded: Boolean = false

    var reviewList = mutableListOf(
        reviews0, reviews1, reviews2, reviews3, reviews4, reviews5, reviews6, reviews7, reviews8, reviews9,
        reviews10, reviews11, reviews12, reviews13, reviews14, reviews15, reviews16, reviews17, reviews18, reviews19,
        reviews20, reviews21, reviews22, reviews23, reviews24, reviews25, reviews26, reviews27, reviews28, reviews29,
        reviews30, reviews31, reviews32, reviews33, reviews34, reviews35, reviews36, reviews37, reviews38, reviews39,
        reviews40, reviews41, reviews42, reviews43, reviews44, reviews45, reviews46
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

    var subjectNumber = mutableListOf<String>(
        // 情報科学類
        "GB42404", // 機械学習
        "GB22611", // 情報可視化
        "GB22031", // システム数理III
        "GB22501", // 情報線形代数
        "GB31901", // 分散システム
        "GB30401", // オペレーティングシステムI
        "GB41511", // 音声聴覚情報処理
        "GB30201", // 計算機アーキテクチャ
        "GB21802", // プログラミングチャレンジ
        "GB40101", // 信号処理概論
        "GB31111", // 並列処理アーキテクチャI
        "GB21601", // オートマトンと形式言語
        "GB20301", // 人工知能
        "GB31501", // ソフトウェア工学
        "GB22401", // インタラクティブCG
        "GB31201", // VLSI工学
        "GB42201", // 画像メディア工学
        "GB31401", // システムプログラム
        "GB41301", // 信号解析
        "GB30111", // コンピュータネットワーク
        "GB21111", // プログラム理論
        "GB22101", // 数理メディア情報学
        "GB31121", // 並列処理アーキテクチャII
        "GB13604", // Mathematics for Computer Science
        "GB31801", // オペレーティングシステムII
        "GB30301", // データベース概論I
        "GB22021", // システム数理II
        "GB42301", // 画像認識工学
        "1F20024", // デジタルクリエイティブ基礎
        "GB32201", // 電子回路
        "GB40301", // ヒューマンインタフェース
        "GB31701", // 情報検索概論
        "GB41711", // 視覚情報科学
        "GB40201", // パターン認識
        "GB40111", // 情報セキュリティ
        "GB20201", // 数理アルゴリズムとシミュレーション
        "GB31301", // プログラム言語処理
        "GB31601", // データベース概論II
        "GB40401", // ディジタル信号処理
        "GB32301", // 人工生命概論
        "GB31801", // オペレーティングシステムII
        "GB36403", // 情報システム実験A
        "GB26403", // ソフトウェアサイエンス実験A
        "GB46403", // 知能情報メディア実験A
        "GB36503", // 情報システム実験B
        "GB26503", // ソフトウェアサイエンス実験B
        "GB46503", // 知能情報メディア実験B
    )

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