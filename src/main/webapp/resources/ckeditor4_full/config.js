/**
 * @license Copyright (c) 2003-2022, CKSource Holding sp. z o.o. All rights reserved.
 * For licensing, see https://ckeditor.com/legal/ckeditor-oss-license
 */

CKEDITOR.editorConfig = function( config ) {
	// Define changes to default configuration here. For example:
	// config.language = 'fr';
	// config.uiColor = '#AADC6E';
	config.toolbar = [
    	{ name: 'styles', items: [  'Format', 'Font', 'FontSize' ] },
		{ name: 'colors', items: [ 'TextColor', 'BGColor' ] },
		{ name: 'basicstyles', items: [ 'Bold', 'Italic', 'Underline', 'Strike', ] },
		{ name: 'paragraph', items: [ 'NumberedList', 'BulletedList', 
		                              'JustifyLeft', 'JustifyCenter', 'JustifyRight', 'JustifyBlock', '-'
		                             ] },
		                             { name: 'links', items: [ 'Link', 'Unlink', 'Anchor' ] },
		{ name: 'insert', items: [ 'Image', 'Table',] },
		{ name: 'document' ,items: [ 'Source' ]},
	];
	config.font_defaultLabel = '나눔고딕'; // 기본 폰트 지정
	config.font_names = '나눔고딕/NanumGothic;돋움/Dotum;돋움체; 굴림;굴림체; 궁서;궁서체; 바탕;바탕체;Arial;Courier New;Times New Roman';
	config.extraAllowedContent = 'address';
	
};

//링크 기본값 : _blank,231212
CKEDITOR.on('dialogDefinition', function ( ev ){
	   if(ev.data.name == 'link'){
	      ev.data.definition.getContents('target').get('linkTargetType')['default']='_blank';
	   }
	});

