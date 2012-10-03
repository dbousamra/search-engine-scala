package search.managers

import scala.Array.canBuildFrom
import scala.collection.mutable.LinkedHashMap

import org.apache.lucene.analysis.standard.StandardAnalyzer
import org.apache.lucene.document.Document
import org.apache.lucene.document.Field
import org.apache.lucene.index.IndexWriter
import org.apache.lucene.queryParser.QueryParser
import org.apache.lucene.search.IndexSearcher
import org.apache.lucene.store.RAMDirectory
import org.apache.lucene.util.Version

import com.thoughtworks.xstream.XStream

import search.documents.{Document => doc}
import search.result.Result

class LuceneSearchManager[T <: doc] {

  val analyzer = new StandardAnalyzer(Version.LUCENE_CURRENT)
  val directory = new RAMDirectory();
  val writer = new IndexWriter(directory, analyzer, IndexWriter.MaxFieldLength.UNLIMITED)
  val mapper = new LinkedHashMap[Int, T]

  def addToIndex(documents: Traversable[T]) = {
    var id = 0
    documents.foreach { d =>
      val doc = simpleDoc(id, d)
      writer.addDocument(doc)
      mapper += id -> d
      id += 1
    }
    writer.commit
    writer.close
  }

  private def simpleDoc(id: Int, d: T) = {
    val doc = new Document()
    doc.add(new Field("content", d.words.mkString(" "), Field.Store.YES, Field.Index.ANALYZED))
    doc.add(new Field("id", id.toString, Field.Store.YES, Field.Index.NO))
    doc
  }

  def query(input: String) = {
    val searcher = new IndexSearcher(directory)
    val q = new QueryParser(Version.LUCENE_36, "content", analyzer).parse(input);
    val docs = searcher.search(q, 100)
    val xstream = new XStream()

    val results = docs.scoreDocs map { docId =>
      val d = searcher.doc(docId.doc)
      val backToDocument = mapper.get(d.get("id").toInt).get
      new Result(backToDocument, docId.score)
    }

    searcher.close
    results.toList
  }
}