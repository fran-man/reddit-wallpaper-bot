package com.franm.wallpaperbot.reddit;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Test;

import com.fasterxml.jackson.databind.JsonNode;

public class ListingParserTest {
	private ListingParser listingParser = new ListingParser("{\"kind\": \"Listing\", \"data\": {\"after\": null, \"dist\": 21, \"facets\": {}, \"modhash\": \"lrv53schz87dd6d2605da222e3b43e49d2631e9d0eebfbdea4\", \"children\": [{\"kind\": \"t3\", \"data\": {\"approved_at_utc\": null, \"subreddit\": \"wallpaper\", \"selftext\": \"\", \"author_fullname\": \"t2_142vt5\", \"saved\": false, \"mod_reason_title\": null, \"gilded\": 0, \"clicked\": false, \"title\": \"Minimalism [3840 x 2160]\", \"link_flair_richtext\": [], \"subreddit_name_prefixed\": \"r/wallpaper\", \"hidden\": false, \"pwls\": 6, \"link_flair_css_class\": null, \"downs\": 0, \"thumbnail_height\": 78, \"hide_score\": false, \"name\": \"t3_cf4xes\", \"quarantine\": false, \"link_flair_text_color\": \"dark\", \"author_flair_background_color\": null, \"subreddit_type\": \"public\", \"ups\": 3081, \"total_awards_received\": 0, \"media_embed\": {}, \"thumbnail_width\": 140, \"author_flair_template_id\": null, \"is_original_content\": false, \"user_reports\": [], \"secure_media\": null, \"is_reddit_media_domain\": true, \"is_meta\": false, \"category\": null, \"secure_media_embed\": {}, \"link_flair_text\": null, \"can_mod_post\": false, \"score\": 3081, \"approved_by\": null, \"thumbnail\": \"https://b.thumbs.redditmedia.com/aMfFiD8mrm9uWAQhrN5vMqZph0tMKajW458Zrd_lPUo.jpg\", \"edited\": false, \"author_flair_css_class\": null, \"author_flair_richtext\": [], \"gildings\": {}, \"post_hint\": \"image\", \"content_categories\": null, \"is_self\": false, \"mod_note\": null, \"created\": 1563553282.0, \"link_flair_type\": \"text\", \"wls\": 6, \"banned_by\": null, \"author_flair_type\": \"text\", \"domain\": \"i.redd.it\", \"allow_live_comments\": false, \"selftext_html\": null, \"likes\": null, \"suggested_sort\": null, \"banned_at_utc\": null, \"view_count\": null, \"archived\": false, \"no_follow\": false, \"is_crosspostable\": true, \"pinned\": false, \"over_18\": false, \"preview\": {\"images\": [{\"source\": {\"url\": \"https://preview.redd.it/rrbltiehz7b31.png?auto=webp&amp;s=50f1077e0cbd13173177ad557650fcd8fe167ea8\", \"width\": 3840, \"height\": 2160}, \"resolutions\": [{\"url\": \"https://preview.redd.it/rrbltiehz7b31.png?width=108&amp;crop=smart&amp;auto=webp&amp;s=6dafcb00ba607d6c4bf3e8a851f9d16d3c45dc83\", \"width\": 108, \"height\": 60}, {\"url\": \"https://preview.redd.it/rrbltiehz7b31.png?width=216&amp;crop=smart&amp;auto=webp&amp;s=f2d2fa70448cf4d5a53a937983fe2803816fdfe9\", \"width\": 216, \"height\": 121}, {\"url\": \"https://preview.redd.it/rrbltiehz7b31.png?width=320&amp;crop=smart&amp;auto=webp&amp;s=54ae8deaffd61a6d9da36cf1bfc5f0a2217c2811\", \"width\": 320, \"height\": 180}, {\"url\": \"https://preview.redd.it/rrbltiehz7b31.png?width=640&amp;crop=smart&amp;auto=webp&amp;s=8f701f66ac2d3158cc318abb1a237b227a6a1e22\", \"width\": 640, \"height\": 360}, {\"url\": \"https://preview.redd.it/rrbltiehz7b31.png?width=960&amp;crop=smart&amp;auto=webp&amp;s=b0f87088d18885ee0334006d69c37490b40f208c\", \"width\": 960, \"height\": 540}, {\"url\": \"https://preview.redd.it/rrbltiehz7b31.png?width=1080&amp;crop=smart&amp;auto=webp&amp;s=0b664f74760dc3aace2c75b9798386a801f78302\", \"width\": 1080, \"height\": 607}], \"variants\": {}, \"id\": \"LZCFjjcyWpj_LTO7vzR045CP1y3E_VFR-9y1L-0Zf9o\"}], \"enabled\": true}, \"all_awardings\": [], \"media_only\": false, \"can_gild\": true, \"spoiler\": false, \"locked\": false, \"author_flair_text\": null, \"visited\": false, \"num_reports\": null, \"distinguished\": null, \"subreddit_id\": \"t5_2qmjl\", \"mod_reason_by\": null, \"removal_reason\": null, \"link_flair_background_color\": \"\", \"id\": \"cf4xes\", \"is_robot_indexable\": true, \"report_reasons\": null, \"author\": \"Dark0de\", \"num_crossposts\": 1, \"num_comments\": 23, \"send_replies\": true, \"whitelist_status\": \"all_ads\", \"contest_mode\": false, \"mod_reports\": [], \"author_patreon_flair\": false, \"author_flair_text_color\": null, \"permalink\": \"/r/wallpaper/comments/cf4xes/minimalism_3840_x_2160/\", \"parent_whitelist_status\": \"all_ads\", \"stickied\": false, \"url\": \"https://i.redd.it/rrbltiehz7b31.png\", \"subreddit_subscribers\": 939054, \"created_utc\": 1563524482.0, \"discussion_type\": null, \"media\": null, \"is_video\": false}}], \"before\": null}}");
	
	@Test
	public void testExtractNonExistentValueReturnsEmptyList() {
		assertEquals(0, listingParser.extractValuesFromResults("noresults").size());
	}
	
	@Test
	public void testExtractValuesReturnsCorrectNumberOfResults() {
		List<JsonNode> nodes = listingParser.extractValuesFromResults("subreddit");
		assertEquals(1, nodes.size());
		assertEquals("wallpaper", actual);
	}
}
