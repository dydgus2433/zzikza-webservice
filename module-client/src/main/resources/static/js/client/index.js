var swiperKV = new Swiper('.kv', {
	loop : true,
	slidesPerView : 1,
	autoplay : {
		delay : 4000, // 딜레이 0
	},
	speed : 300, // 슬라이드 속도 2초
	pagination : {
		el : '.swiper-pagination',
		clickable : true,
	},
	navigation : {
		nextEl : '.swiper-button-next1',
		prevEl : '.swiper-button-prev1',
	},
});
var swiperGW = new Swiper('.goods.goodsWrap', {
	loop : true,
	slidesPerView : 3,
	spaceBetween : 30,
	autoplay : {
		delay : 4000, // 딜레이 0
	},
	speed : 300, // 슬라이드 속도 2초
	navigation : {
		nextEl : '.next-goods',
		prevEl : '.prev-goods',
	},
	breakpoints : {
		480 : {
			slidesPerView : 2,
			spaceBetween : 20,
		},
		320 : {
			slidesPerView : 1,
			spaceBetween : 10,
		}
	}
});

var swiperEXH = new Swiper('.exh.goodsWrap', {
	loop : true,
	slidesPerView : 3,
	spaceBetween : 30,
	autoplay : {
		delay : 4000, // 딜레이 0
	},
	speed : 300, // 슬라이드 속도 2초
	navigation : {
		nextEl : '.next-exh-goods',
		prevEl : '.prev-exh-goods',
	},
	breakpoints : {
		480 : {
			slidesPerView : 2,
			spaceBetween : 20,
		},
		320 : {
			slidesPerView : 1,
			spaceBetween : 10,
		}
	}
});

var swiperBN = new Swiper('.banner', {
	loop : true,
	slidesPerView : 1,
	autoplay : {
		delay : 4000, // 딜레이 0
	},
	speed : 300, // 슬라이드 속도 2초
	pagination : {
		el : '.swiper-pagination',
		clickable : true,
	},
});
