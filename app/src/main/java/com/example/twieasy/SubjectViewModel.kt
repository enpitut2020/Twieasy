package com.example.twieasy

import androidx.lifecycle.ViewModel

data class Subject(
    var name: String,
    var info: String,
    var easiness: Int,
    var reviews: MutableCollection<String>,
)//構造体みたいなクラス

enum class Department {
    COINS, MAST, KLIS
}

//情報科学類
private var reviews0: MutableCollection<String> = mutableListOf()
private var reviews1: MutableCollection<String> = mutableListOf()
private var reviews2: MutableCollection<String> = mutableListOf()
private var reviews3: MutableCollection<String> = mutableListOf()
private var reviews4: MutableCollection<String> = mutableListOf()
private var reviews5: MutableCollection<String> = mutableListOf()
private var reviews6: MutableCollection<String> = mutableListOf()
private var reviews7: MutableCollection<String> = mutableListOf()
private var reviews8: MutableCollection<String> = mutableListOf()
private var reviews9: MutableCollection<String> = mutableListOf()
private var reviews10: MutableCollection<String> = mutableListOf()
private var reviews11: MutableCollection<String> = mutableListOf()
private var reviews12: MutableCollection<String> = mutableListOf()
private var reviews13: MutableCollection<String> = mutableListOf()
private var reviews14: MutableCollection<String> = mutableListOf()
private var reviews15: MutableCollection<String> = mutableListOf()
private var reviews16: MutableCollection<String> = mutableListOf()
private var reviews17: MutableCollection<String> = mutableListOf()
private var reviews18: MutableCollection<String> = mutableListOf()
private var reviews19: MutableCollection<String> = mutableListOf()
private var reviews20: MutableCollection<String> = mutableListOf()
private var reviews21: MutableCollection<String> = mutableListOf()
private var reviews22: MutableCollection<String> = mutableListOf()
private var reviews23: MutableCollection<String> = mutableListOf()
private var reviews24: MutableCollection<String> = mutableListOf()
private var reviews25: MutableCollection<String> = mutableListOf()
private var reviews26: MutableCollection<String> = mutableListOf()
private var reviews27: MutableCollection<String> = mutableListOf()
private var reviews28: MutableCollection<String> = mutableListOf()
private var reviews29: MutableCollection<String> = mutableListOf()
private var reviews30: MutableCollection<String> = mutableListOf()
private var reviews31: MutableCollection<String> = mutableListOf()
private var reviews32: MutableCollection<String> = mutableListOf()
private var reviews33: MutableCollection<String> = mutableListOf()
private var reviews34: MutableCollection<String> = mutableListOf()
private var reviews35: MutableCollection<String> = mutableListOf()
private var reviews36: MutableCollection<String> = mutableListOf()
private var reviews37: MutableCollection<String> = mutableListOf()
private var reviews38: MutableCollection<String> = mutableListOf()
private var reviews39: MutableCollection<String> = mutableListOf()
private var reviews40: MutableCollection<String> = mutableListOf()
private var reviews41: MutableCollection<String> = mutableListOf()
private var reviews42: MutableCollection<String> = mutableListOf()
private var reviews43: MutableCollection<String> = mutableListOf()
private var reviews44: MutableCollection<String> = mutableListOf()
private var reviews45: MutableCollection<String> = mutableListOf()
private var reviews46: MutableCollection<String> = mutableListOf()

//情報メディア創成学類
private var reviews47: MutableCollection<String> = mutableListOf()
private var reviews48: MutableCollection<String> = mutableListOf()
private var reviews49: MutableCollection<String> = mutableListOf()
private var reviews50: MutableCollection<String> = mutableListOf()
private var reviews51: MutableCollection<String> = mutableListOf()
private var reviews52: MutableCollection<String> = mutableListOf()
private var reviews53: MutableCollection<String> = mutableListOf()
private var reviews54: MutableCollection<String> = mutableListOf()
private var reviews55: MutableCollection<String> = mutableListOf()
private var reviews56: MutableCollection<String> = mutableListOf()
private var reviews57: MutableCollection<String> = mutableListOf()
private var reviews58: MutableCollection<String> = mutableListOf()
private var reviews59: MutableCollection<String> = mutableListOf()
private var reviews60: MutableCollection<String> = mutableListOf()
private var reviews61: MutableCollection<String> = mutableListOf()
private var reviews62: MutableCollection<String> = mutableListOf()
private var reviews63: MutableCollection<String> = mutableListOf()
private var reviews64: MutableCollection<String> = mutableListOf()
private var reviews65: MutableCollection<String> = mutableListOf()
private var reviews66: MutableCollection<String> = mutableListOf()
private var reviews67: MutableCollection<String> = mutableListOf()
private var reviews68: MutableCollection<String> = mutableListOf()
private var reviews69: MutableCollection<String> = mutableListOf()
private var reviews70: MutableCollection<String> = mutableListOf()
private var reviews71: MutableCollection<String> = mutableListOf()
private var reviews72: MutableCollection<String> = mutableListOf()
private var reviews73: MutableCollection<String> = mutableListOf()
private var reviews74: MutableCollection<String> = mutableListOf()
private var reviews75: MutableCollection<String> = mutableListOf()
private var reviews76: MutableCollection<String> = mutableListOf()
private var reviews77: MutableCollection<String> = mutableListOf()
private var reviews78: MutableCollection<String> = mutableListOf()
private var reviews79: MutableCollection<String> = mutableListOf()
private var reviews80: MutableCollection<String> = mutableListOf()
private var reviews81: MutableCollection<String> = mutableListOf()
private var reviews82: MutableCollection<String> = mutableListOf()
private var reviews83: MutableCollection<String> = mutableListOf()
private var reviews84: MutableCollection<String> = mutableListOf()
private var reviews85: MutableCollection<String> = mutableListOf()
private var reviews86: MutableCollection<String> = mutableListOf()

//知識情報・図書館学類
private var reviews87: MutableCollection<String> = mutableListOf()
private var reviews88: MutableCollection<String> = mutableListOf()
private var reviews89: MutableCollection<String> = mutableListOf()
private var reviews90: MutableCollection<String> = mutableListOf()
private var reviews91: MutableCollection<String> = mutableListOf()
private var reviews92: MutableCollection<String> = mutableListOf()
private var reviews93: MutableCollection<String> = mutableListOf()
private var reviews94: MutableCollection<String> = mutableListOf()
private var reviews95: MutableCollection<String> = mutableListOf()
private var reviews96: MutableCollection<String> = mutableListOf()
private var reviews97: MutableCollection<String> = mutableListOf()
private var reviews98: MutableCollection<String> = mutableListOf()
private var reviews99: MutableCollection<String> = mutableListOf()
private var reviews100: MutableCollection<String> = mutableListOf()
private var reviews101: MutableCollection<String> = mutableListOf()
private var reviews102: MutableCollection<String> = mutableListOf()
private var reviews103: MutableCollection<String> = mutableListOf()
private var reviews104: MutableCollection<String> = mutableListOf()
private var reviews105: MutableCollection<String> = mutableListOf()
private var reviews106: MutableCollection<String> = mutableListOf()
private var reviews107: MutableCollection<String> = mutableListOf()
private var reviews108: MutableCollection<String> = mutableListOf()
private var reviews109: MutableCollection<String> = mutableListOf()
private var reviews110: MutableCollection<String> = mutableListOf()
private var reviews111: MutableCollection<String> = mutableListOf()
private var reviews112: MutableCollection<String> = mutableListOf()
private var reviews113: MutableCollection<String> = mutableListOf()
private var reviews114: MutableCollection<String> = mutableListOf()
private var reviews115: MutableCollection<String> = mutableListOf()
private var reviews116: MutableCollection<String> = mutableListOf()
private var reviews117: MutableCollection<String> = mutableListOf()
private var reviews118: MutableCollection<String> = mutableListOf()
private var reviews119: MutableCollection<String> = mutableListOf()
private var reviews120: MutableCollection<String> = mutableListOf()
private var reviews121: MutableCollection<String> = mutableListOf()
private var reviews122: MutableCollection<String> = mutableListOf()
private var reviews123: MutableCollection<String> = mutableListOf()
private var reviews124: MutableCollection<String> = mutableListOf()
private var reviews125: MutableCollection<String> = mutableListOf()
private var reviews126: MutableCollection<String> = mutableListOf()
private var reviews127: MutableCollection<String> = mutableListOf()
private var reviews128: MutableCollection<String> = mutableListOf()
private var reviews129: MutableCollection<String> = mutableListOf()
private var reviews130: MutableCollection<String> = mutableListOf()
private var reviews131: MutableCollection<String> = mutableListOf()
private var reviews132: MutableCollection<String> = mutableListOf()
private var reviews133: MutableCollection<String> = mutableListOf()
private var reviews134: MutableCollection<String> = mutableListOf()
private var reviews135: MutableCollection<String> = mutableListOf()
private var reviews136: MutableCollection<String> = mutableListOf()
private var reviews137: MutableCollection<String> = mutableListOf()
private var reviews138: MutableCollection<String> = mutableListOf()
private var reviews139: MutableCollection<String> = mutableListOf()
private var reviews140: MutableCollection<String> = mutableListOf()
private var reviews141: MutableCollection<String> = mutableListOf()
private var reviews142: MutableCollection<String> = mutableListOf()
private var reviews143: MutableCollection<String> = mutableListOf()
private var reviews144: MutableCollection<String> = mutableListOf()
private var reviews145: MutableCollection<String> = mutableListOf()
private var reviews146: MutableCollection<String> = mutableListOf()
private var reviews147: MutableCollection<String> = mutableListOf()
private var reviews148: MutableCollection<String> = mutableListOf()
private var reviews149: MutableCollection<String> = mutableListOf()
private var reviews150: MutableCollection<String> = mutableListOf()
private var reviews151: MutableCollection<String> = mutableListOf()
private var reviews152: MutableCollection<String> = mutableListOf()
private var reviews153: MutableCollection<String> = mutableListOf()
private var reviews154: MutableCollection<String> = mutableListOf()
private var reviews155: MutableCollection<String> = mutableListOf()
private var reviews156: MutableCollection<String> = mutableListOf()
private var reviews157: MutableCollection<String> = mutableListOf()
private var reviews158: MutableCollection<String> = mutableListOf()
private var reviews159: MutableCollection<String> = mutableListOf()
private var reviews160: MutableCollection<String> = mutableListOf()
private var reviews161: MutableCollection<String> = mutableListOf()
private var reviews162: MutableCollection<String> = mutableListOf()
private var reviews163: MutableCollection<String> = mutableListOf()
private var reviews164: MutableCollection<String> = mutableListOf()
private var reviews165: MutableCollection<String> = mutableListOf()
private var reviews166: MutableCollection<String> = mutableListOf()
private var reviews167: MutableCollection<String> = mutableListOf()
private var reviews168: MutableCollection<String> = mutableListOf()
private var reviews169: MutableCollection<String> = mutableListOf()
private var reviews170: MutableCollection<String> = mutableListOf()


class SubjectViewModel: ViewModel() {

    var loaded: Boolean = false

    var currentAccount: String = ""

    var reviewList = mutableListOf(
        reviews0, reviews1, reviews2, reviews3, reviews4, reviews5, reviews6, reviews7, reviews8, reviews9,
        reviews10, reviews11, reviews12, reviews13, reviews14, reviews15, reviews16, reviews17, reviews18, reviews19,
        reviews20, reviews21, reviews22, reviews23, reviews24, reviews25, reviews26, reviews27, reviews28, reviews29,
        reviews30, reviews31, reviews32, reviews33, reviews34, reviews35, reviews36, reviews37, reviews38, reviews39,
        reviews40, reviews41, reviews42, reviews43, reviews44, reviews45, reviews46, reviews47, reviews48, reviews49,
        reviews50, reviews51, reviews52, reviews53, reviews54, reviews55, reviews56, reviews57, reviews58, reviews59,
        reviews60, reviews61, reviews62, reviews63, reviews64, reviews65, reviews66, reviews67, reviews68, reviews69,
        reviews70, reviews71, reviews72, reviews73, reviews74, reviews75, reviews76, reviews77, reviews78, reviews79,
        reviews80, reviews81, reviews82, reviews83, reviews84, reviews85, reviews86, reviews87, reviews88, reviews89,
        reviews90, reviews91, reviews92, reviews93, reviews94, reviews95, reviews96, reviews97, reviews98, reviews99,
        reviews100, reviews101, reviews102, reviews103, reviews104, reviews105, reviews106, reviews107, reviews108, reviews109,
        reviews110, reviews111, reviews112, reviews113, reviews114, reviews115, reviews116, reviews117, reviews118, reviews119,
        reviews120, reviews121, reviews122, reviews123, reviews124, reviews125, reviews126, reviews127, reviews128, reviews129,
        reviews130, reviews131, reviews132, reviews133, reviews134, reviews135, reviews136, reviews137, reviews138, reviews139,
        reviews140, reviews141, reviews142, reviews143, reviews144, reviews145, reviews146, reviews147, reviews148, reviews149,
        reviews150, reviews151, reviews152, reviews153, reviews154, reviews155, reviews156, reviews157, reviews158, reviews159,
        reviews160, reviews161, reviews162, reviews163, reviews164, reviews165, reviews166, reviews167, reviews168, reviews169,
        reviews170
    )

    var coinsSubjectNumber = mutableListOf<String>(
        // 情報科学類(47個)
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
    )

    var mastSubjectNumber = mutableListOf<String>(
        // 情報メディア創成学類(40個)
        "GB26503", // ソフトウェアサイエンス実験B
        "GB46503", // 知能情報メディア実験B
        /*"GC20201",//コンテンツビジネス・マーケティング
        "GC21601",//情報数学 C
        "GC22201",//プログラム言語論
        "GC23401",//パターン認識
        "GC23501",//画像・映像情報処理
        "GC23601",//音声・音響学基礎
        "GC25301",//コンピュータネットワーク
        "GC27701",//ACPC 連携講座: ライブ・コンテンツ論　
        "GC27902",//ハイブリッドアート演習
        "GC41103",//情報メディア実験 A
        "GC41203",//情報メディア実験 B
        "GC50201",//オートマトンと形式言語
        "GC50501",//通信ネットワーク
        "GC50701",//インタラクティブ CG
        "GC51101",//音楽・音響情報処理
        "GC51401",//システム運用・管理
        "GC51701",//実世界指向システム
        "GC51901",//マークアップ言語
        "GC52101",//データベースシステム II
        "GC52301",//先端技術とメディア表現
        "GC52401",//コンテンツプロデュース論
        "GC52701",//ソフトウェア構成
        "GC52801",//情報デザイン II
        "GC52901",//インタラクションデザイン
        "GC53303",//ディジタルコンテンツ表現実習
        "GC53401",//ディジタルドキュメント
        "GC53502",//エンタテインメントコンピューティング演習
        "GC53601",//視覚情報科学
        "GC53701",//システム数理 I
        "GC53801",//システム数理 II
        "GC53901",//知識・自然言語処理
        "GC54001",//情報可視化
        "GC54301",//システム数理 III
        "GC54501",//インターネット動画メディア論
        "GC54701",//知覚心理学
        "GC54801",//数式処理システム論
        "GC59101",//情報メディア創成特別講義 A
        "GC59201",//情報メディア創成特別講義 B
        "GC59601",//情報メディア創成特別講義 F
        "GC52201",//情報数学 IV*/
    )

    var klisSubjectNumber = mutableListOf<String>(
        // 知識情報・図書館学類(84個)
        "GE32053", //知的探求の世界II-5
        "GE32063", //知的探求の世界II-6
        "GA40103", //体験型システム開発A
        "GA40203", //体験型システム開発B
        "GA40303", //ビジネスシステムデザ インA
        "GA40403", //ビジネスシステムデザ インB
        "GE40201", //学習指導と学校図書館
        "GE40301", //学校図書館メディアの 構成
        "GE40401", //読書と豊かな人間性
        "GE40501", //情報メディアの活用
        "GE40603", //インターンシップ
        "GE40703", //国際インターンシップ
        "GE42002", //国際学術演習A
        "GE42102", //国際学術演習B
        "GE50712", //専門英語B-1
        "GE50722", //専門英語B-2
        "GE50732", //専門英語B-3
        "GE50812", //専門英語C-1
        "GE50822", //専門英語C-2
        "GE50832", //専門英語C-3
        "GE60103", //知識科学実習
        "GE60201", //テクニカルコミュニ ケーション
        "GE61701", //サイエンスコミュニ ケーション
        "GE60501", //知識論
        "GE60601", //知識形成論
        "GE60701", //レファレンスサイエン ス
        "GE62101", //知識コミュニケーショ ン
        "GE62201", //メディア社会文化論
        "GE60801", //学術メディア論
        "GE61001", //コミュニティ情報論
        "GE61101", //図書館建築論
        "GE62301", //ソーシャルメディア分 析
        "GE61201", //知識構造化法
        "GE61301", //情報評価
        "GE61501", //データマイニング
        "GE61801", //データ構造とアルゴリ ズム
        "GE61901", //情報検索システム
        "GE62001", //生命情報学
        "GE62401", //Machine Learning and Information Retrieval
        "GE70103", //知識情報システム実習
        "GE70201", //情報サービスシステム
        "GE70301", //ディジタルドキュメン ト
        "GE70401", //ディジタルライブラリ
        "GE72101", //経営情報システム論
        "GE72201", //アーカイブズの利用
        "GE72301", //アーカイブズの構築
        "GE70501", //情報検索システム
        "GE70601", //Webプログラミング
        "GE70701", //マルチメディアシステ ム
        "GE72701", //Machine Learning and Information Retrieval
        "GE70801", //データ表現と処理
        "GE70901", //データベース技術
        "GE71001", //情報デザインとインタ フェース
        "GE71011", //情報デザインとインタ フェース
        "GE73001", //情報デザインとインタ フェースB
        "GE71101", //ヒューマンインタ フェース
        "GE72501", //メディアアート
        "GE72601", //Human-computer Interaction
        "GE72801", //知識資源の分類と索引
        "GE71501", //クラウドコンピュー ティング
        "GE71701", //テキスト処理
        "GE71801", //データ構造とアルゴリ ズム
        "GE71901", //マークアップ言語
        "GE72001", //ソフトウェア工学
        "GE72901", //数式処理システム論
        "GE80103", //情報資源経営実習
        "GE80201", //図書館論
        "GE80301", //学術情報基盤論
        "GE80401", //経営情報システム論
        "GE80501", //情報サービス経営論
        "GE82601", //パブリックガバナンス
        "GE80801", //情報サービス構成論
        "GE80901", //コレクションとアクセス
        "GE82802", //PBL型図書館サービス
        "GE81001", //教育文化政策
        "GE81101", //メディア教育の実践と評価
        "GE81201", //学校図書館論
        "GE81301", //情報法
        "GE81401", //知的財産権論A
        "GE82701", //インターネット動画メディア論
        "GE81901", //図書館文化史論
        "GE82101", //日本図書学
        "GE82201", //アーカイブズの構築
        "GE82401", //アーカイブズの利用

    )

    // KDBからとってきた生データ
    var coinsKdbRawData = mutableListOf<String>()
    var coinsSubjects = ArrayList<Subject>()

    var mastKdbRawData = mutableListOf<String>()
    var mastSubjects = ArrayList<Subject>()

    var klisKdbRawData = mutableListOf<String>()
    var klisSubjects = ArrayList<Subject>()

    //@JvmField var subjects = ArrayList<Subject>()
}