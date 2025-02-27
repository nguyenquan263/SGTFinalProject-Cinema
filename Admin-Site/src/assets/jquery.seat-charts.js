/*!
 * jQuery-Seat-Charts v1.1.1
 * https://github.com/mateuszmarkowski/jQuery-Seat-Charts
 *
 * Copyright 2013, 2014 Mateusz Markowski
 * Released under the MIT license
 */

(function($) {
		
	//'use strict';	
		
	$.fn.seatCharts = function (setup) {

		//if there's seatCharts object associated with the current element, return it
		if (this.data('seatCharts')) {
			return this.data('seatCharts');
		}
		
		var fn       = this,
			seats    = {},
			seatIds  = [],
			
			settings = {
			
				naming  : {
					top    : true,
					left   : true,
					getId  : function(character, row, column) {
						return row + '_' + column;
					},
					getLabel : function (character, row, column) {
						return column;
					}
					
				},
				legend : {
					node   : null,
					items  : []
				},
				click   : function() {

					if (this.status() == 'available') {
						return 'selected';
					} else if (this.status() == 'selected') {
						return 'available';
					} else {
						return this.style();
					}
					
				},		
				seats   : {}
			},
			//seat will be basically a seat object which we'll when generating the map
			seat = (function(seatCharts, seatChartsSettings) {
				return function (setup) {
					var fn = this;
					
					fn.settings = $.extend({
						status : 'available', //available, unavailable, selected
						style  : 'available',
						//make sure there's an empty hash if user doesn't pass anything
						data   : seatChartsSettings.seats[setup.character] || {}
						//anything goes here?
					}, setup);

					fn.settings.$node = $('<div></div>');
					
					fn.settings.$node
						.attr({
							id             : fn.settings.id,
							role           : 'checkbox',
							'aria-checked' : false,
							focusable      : true,
							tabIndex       : -1 //manual focus
						})
						.text(fn.settings.label)
						.addClass(['seatCharts-seat', 'seatCharts-cell', 'available'].concat(
							//let's merge custom user defined classes with standard JSC ones
							fn.settings.classes, 
							typeof seatChartsSettings.seats[fn.settings.character] == "undefined" ? 
								[] : seatChartsSettings.seats[fn.settings.character].classes
							).join(' '));
					
					//basically a wrapper function
					fn.data = function() {
						return fn.settings.data;
					};
					
					fn.char = function() {
						return fn.settings.character;
					};
					
					fn.node = function() {
						return fn.settings.$node;						
					};

					/*
					 * Can either set or return status depending on arguments.
					 *
					 * If there's no argument, it will return the current style.
					 *
					 * If you pass an argument, it will update seat's style
					 */
					fn.style = function() {

						return arguments.length == 1 ?
							(function(newStyle) {
								var oldStyle = fn.settings.style;

								//if nothing changes, do nothing
								if (newStyle == oldStyle) {
									return oldStyle;
								}
								
								//focused is a special style which is not associated with status
								fn.settings.status = newStyle != 'focused' ? newStyle : fn.settings.status;
								fn.settings.$node
									.attr('aria-checked', newStyle == 'selected');

								//if user wants to animate status changes, let him do this
								seatChartsSettings.animate ?
									fn.settings.$node.switchClass(oldStyle, newStyle, 200) :
									fn.settings.$node.removeClass(oldStyle).addClass(newStyle);			
								return fn.settings.style = newStyle;
							})(arguments[0]) : fn.settings.style;
					};
					
					//either set or retrieve
					fn.status = function() {

						return fn.settings.status = arguments.length == 1 ? 
							fn.style(arguments[0]) : fn.settings.status;
					};
					
					//using immediate function to convienietly get shortcut variables
					(function(seatSettings, character, seat) {
						//attach event handlers
						$.each(['click'], function(index, callback) {
						
							//we want to be able to call the functions for each seat object
							fn[callback] = function() {
							
								return fn.style(typeof seatSettings[character][callback] === 'function' ?
									seatSettings[character][callback].apply(seat) : seatChartsSettings[callback].apply(seat));
							};
							
						});
					//the below will become seatSettings, character, seat thanks to the immediate function		
					})(seatChartsSettings.seats, fn.settings.character, fn);
							
					fn.node()
						//the first three mouse events are simple
						.on('click',fn.click)
				}
			})(fn, settings);
			
		fn.addClass('seatCharts-container');
		
		//true -> deep copy!
		$.extend(true, settings, setup);		
		
		//Generate default row ids unless user passed his own
		settings.naming.rows = settings.naming.rows || (function(length) {
			var rows = [];
			
			/*var data=settings.map;*/
			/*data=String(data);
			alert(data);
			var str=data.split(',');
			alert(str[2]);*/
			for (var i = 1; i<= length-2; i++) {
				if(i==4){
				rows.push(" ");
			
				}
				if(i==10){
				rows.push(" ");
				rows.push(String.fromCharCode(74));
				}
				else{
				rows.push(String.fromCharCode(65+i-1));
				}
			}
			return rows;
		})(settings.map.length);
		
		//Generate default column ids unless user passed his own
		settings.naming.columns = settings.naming.columns || (function(length) {
			var columns = [];
			
			for (var i = 1; i <= length-2; i++) {	
					if(i==3){
						columns.push(" ")
					}
					if(i==10){
						columns.push(" ")
					}
					columns.push(i);
					
			}
			
			return columns;
		})(settings.map[0].split('').length);
		
		if (settings.naming.top) {
			var $headerRow = $('<div ></div>')
				.addClass('seatCharts-row seatCharts-header');
			
			if (settings.naming.left) {
				$headerRow.append($('<div></div>').addClass('seatCharts-cell'));
			}
			$.each(settings.naming.columns, function(index, value) {
				$headerRow.append(
					$('<div></div>')
						.addClass('seatCharts-cell')
						.text(value)
				);
			});
		}
		
		fn.append($headerRow);
		
		//do this for each map row
		$.each(settings.map, function(row, characters) {

			var $row = $('<div></div>').addClass('seatCharts-row');
				
			if (settings.naming.left) {
				$row.append(
					$('<div></div>')
						.addClass('seatCharts-cell seatCharts-space')
						.text(settings.naming.rows[row])
				);
			}

			/*
			 * Do this for each seat (letter)
			 *
			 * Now users will be able to pass custom ID and label which overwrite the one that seat would be assigned by getId and
			 * getLabel
			 *
			 * New format is like this:
			 * a[ID,label]a[ID]aaaaa
			 *
			 * So you can overwrite the ID or label (or both) even for just one seat.
			 * Basically ID should be first, so if you want to overwrite just label write it as follows:
			 * a[,LABEL]
			 *
			 * Allowed characters in IDs areL 0-9, a-z, A-Z, _
			 * Allowed characters in labels are: 0-9, a-z, A-Z, _, ' ' (space)
			 *
			 */
			 
			$.each(characters.match(/[a-z_]{1}/gi), function (column, characterParams) { 
				var matches         = characterParams.match(/([a-z_]{1})/i),
					//no matter if user specifies [] params, the character should be in the second element
					character       = matches[1],
					//check if user has passed some additional params to override id or label
					params          = typeof matches[3] !== 'undefined' ? matches[3].split(',') : [],
					//id param should be first
					overrideId      = params.length ? params[0] : null,
					//label param should be second
					overrideLabel   = params.length === 2 ? params[1] : null;
								
				$row.append(character != '_' ?
					//if the character is not an underscore (empty space)
					(function(naming) {
	
						//so users don't have to specify empty objects
						settings.seats[character] = character in settings.seats ? settings.seats[character] : {};
	
						var id = overrideId ? overrideId : naming.getId(character, naming.rows[row], naming.columns[column]);
						seats[id] = new seat({
							id        : id,
							label     : overrideLabel ?
								overrideLabel : naming.getLabel(character, naming.rows[row], naming.columns[column]),
							row       : row,
							column    : column,
							character : character
						});

						seatIds.push(id);
						return seats[id].node();
						
					})(settings.naming) :
					//this is just an empty space (_)
					$('<div></div>').addClass('seatCharts-cell seatCharts-space')	
				);
			});
			
			fn.append($row);
		});
	
		//if there're any legend items to be rendered
		settings.legend.items.length ? (function(legend) {
			//either use user-defined container or create our own and insert it right after the seat chart div
			var $container = (legend.node || $('<div></div').insertAfter(fn))
				.addClass('seatCharts-legend');
				
			var $ul = $('<ul class="list-unstyled"></ul>')
				.addClass('seatCharts-legendList')
				.appendTo($container);
			
			$.each(legend.items, function(index, item) {
				$ul.append(
					$('<li></li>')
						.addClass('seatCharts-legendItem')
						.append(
							$('<div></div>')
								//merge user defined classes with our standard ones
								.addClass(['seatCharts-seat', 'seatCharts-cell', item[1]].concat(
									settings.classes, 
									typeof settings.seats[item[0]] == "undefined" ? [] : settings.seats[item[0]].classes).join(' ')
								)
						)
						.append(
							$('<span></span>')
								.addClass('seatCharts-legendDescription')
								.text(item[2])
						)
				);
			});
			
			return $container;
		})(settings.legend) : null;
	
		fn.attr({
			tabIndex : 0
		});
		
		
		//when container's focused, move focus to the first seat
		fn.focus(function() {
			if (fn.attr('aria-activedescendant')) {
				seats[fn.attr('aria-activedescendant')].blur();
			}
				
			fn.find('.seatCharts-seat:not(.seatCharts-space):first').focus();
			seats[seatIds[0]].focus();

		});
	
		//public methods of seatCharts
		fn.data('seatCharts', {
			seats   : seats,
			seatIds : seatIds,
			//set for one, set for many, get for one
			status: function() {
				var fn = this;
			
				return arguments.length == 1 ? fn.seats[arguments[0]].status() : (function(seatsIds, newStatus) {
				
					return typeof seatsIds == 'string' ? fn.seats[seatsIds].status(newStatus) : (function() {
						$.each(seatsIds, function(index, seatId) {
							fn.seats[seatId].status(newStatus);
						});
					})();
				})(arguments[0], arguments[1]);
			},
			each  : function(callback) {
				var fn = this;
			
				for (var seatId in fn.seats) {
					if (false === callback.call(fn.seats[seatId], seatId)) {
						return seatId;//return last checked
					}
				}
				
				return true;
			},
			node       : function() {
				var fn = this;
				//basically create a CSS query to get all seats by their DOM ids
				return $('#' + fn.seatIds.join(',#'));
			},

			find       : function(query) {//D, a.available, unavailable
				var fn = this;
			
				var seatSet = fn.set();
			
				//user searches just for a particual character
				return query.length == 1 ? (function(character) {
					fn.each(function() {
						if (this.char() == character) {
							seatSet.push(this.settings.id, this);
						}
					});
					
					return seatSet;
				})(query) : (function() {
					//user runs a more sophisticated query, so let's see if there's a dot
					return query.indexOf('.') > -1 ? (function() {
						//there's a dot which separates character and the status
						var parts = query.split('.');
						
						fn.each(function(seatId) {
							if (this.char() == parts[0] && this.status() == parts[1]) {
								seatSet.push(this.settings.id, this);
							}
						});
						
						return seatSet;
					})() : (function() {
						fn.each(function() {

							if (this.status() == query) {
								seatSet.push(this.settings.id, this);
							}
						});
						
						return seatSet;
					})();
				})();
				
			},
			set        : function set() {//inherits some methods
				var fn = this;
				
				return {
					seats      : [],
					seatIds    : [],
					length     : 0,
					status     : function() {
						var args = arguments,
							that = this;
						//if there's just one seat in the set and user didn't pass any params, return current status
						return this.length == 1 && args.length == 0 ? this.seats[0].status() : (function() {
							//otherwise call status function for each of the seats in the set
							$.each(that.seats, function() {
								this.status.apply(this, args);
							});
						})();
					},
					node       : function() {
						return fn.node.call(this);
					},
					each       : function() {
						return fn.each.call(this, arguments[0]);
					},
					get        : function() {
						return fn.get.call(this, arguments[0]);
					},
					find       : function() {
						return fn.find.call(this, arguments[0]);
					},
					set       : function() {
						return set.call(fn);
					},
					push       : function(id, seat) {
						this.seats.push(seat);
						this.seatIds.push(id);
						++this.length;
					}
				};
			},
			//get one object or a set of objects
			get   : function(seatsIds) {
				var fn = this;

				return typeof seatsIds == 'string' ? 
					fn.seats[seatsIds] : (function() {
						
						var seatSet = fn.set();
						
						$.each(seatsIds, function(index, seatId) {
							if (typeof fn.seats[seatId] === 'object') {
								seatSet.push(seatId, fn.seats[seatId]);
							}
						});
						
						return seatSet;
					})();
			}
		});
		
		return fn.data('seatCharts');
	}
	
	
})(jQuery);
