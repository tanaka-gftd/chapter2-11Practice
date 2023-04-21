//N予備校 Scala学習2-11 問題

//初級問題
//Userクラスのコンパニオンオブジェクトを作り、printAgeという年齢をコンソールに出力するメソッドを作成する

//中級問題
//User("ユータ,17")のような表記でも、Userクラスのインスタンスを作成できるファクトリメソッドを実装

//上級問題
//提示されたオブジェクト内の変数に大量のデータを入れ、意図的にjava.lang.OutOfMemoryErrorを引き起こす


//Userクラス
//コンストラクタとして、2つの引数を取る
class User (private val name: String, private val age: Int){  //nameとageはprivateなフィールドとして保持される
  override def toString = s"User($name, $age)"
}

//Userオブジェクト(Userクラスのコンパニオンオブジェクト)
object User {

  //初級問題
  //この実装で正解だが、解答をみる限り、初級問題ではファクトリメソッドを作る必要はないようだ(インスタンス作成時にnewしてるので)
  def apply(name: String, age: Int) = new User(name,age)  //ファクトリメソッド
  def printAge(user: User): Unit = println(user.age)  //privateなageフィールドの値を表示する

  //中級問題
  //split(",") で、引数として渡された文字列を分割する
  //文字列扱いになっているので、数値部分はtoIntしておく
  def apply(arg: String) = {
    val arg_split = arg.split(",")
    new User(arg_split(0), arg_split(1).toInt)
  }

  //中級問題_解答
  //解答ではコンストラクタに渡す引数の中身について理解しやすくするためか、分割した文字列をそれぞれ一旦別の変数に格納してからnewしている
  /*
  def apply(string: String): User = {
    val parts = string.split(",")
    val name = parts(0)  //分割した文字列の内、name部分を変数に格納
    val age = parts(1).toInt  //分割した文字列の内、age部分をtoIntしてから格納
    new User(name, age)
  }
  */
}



//上級問題
//mapにデータを大量に入れて、意図的にjava.lang.OutOfMemoryErrorという例外を発生させる
//分からないので、解答を参照しました(理解しやすいよう、Hellosオブジェクト内のコードを少し改変しています)
object Cache {
  var map: Map[Int, String] = Map(0 -> "")
}

object Hellos extends App {  //Appトレイトをしているので、単独で実行できる(その代わり、REPLからでは実行できない？)

  //1億回まで hello! という文字列をつなげ合わせた文字列をCache.mapの中に格納しようとするプログラム
  //実行すると java.lang.OutOfMemoryError が発生する
  val count = 100000000

  //0からcountで設定した値(100000000)までループ
  //Cacheオブジェクトのmapから、iをkeyとしてvalueを取得
  //現在のCache.mapに新しいエントリーを追加
  // hello! の文字列の結合を繰り返す
  for (i <- 0 to count) {
    val hellos = Cache.map(i)
    Cache.map = Cache.map + ((i + 1) -> (hellos + "hello!"))
  }
  println(Cache.map(count))
}
