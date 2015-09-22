package us.feras.mdv.demo;

import us.feras.mdv.MarkdownView;
import android.app.Activity;
import android.os.Bundle;
import android.view.Window;

public class LocalMarkdownActivity extends Activity {
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		MarkdownView webView = new MarkdownView(this);
		setContentView(webView);
		//String markdownTest = getResources().getString(R.string.markdown_test);
		webView.loadMarkdownFile("file:///android_asset/hello.md", "file:///android_asset/markdown_css_themes/foghorn.css");
		//webView.loadMarkdown(markdownTest, "file:///android_asset/markdown_css_themes/foghorn.css");
	}
}
