module.exports = function (grunt) {

    // load grunt plugins
    require('load-grunt-tasks')(grunt);

    // project configuration
    grunt.initConfig({
        pkg: grunt.file.readJSON('package.json'),

        clean: {
            dist: {
                src: ['dist', 'src/main/resources/static/*.min.js']
            }
        },

        babel: {
            dist: {
                files: {
                    'dist/app.js': ['src/main/javascript/app.jsx']
                }
            }
        },

        browserify: {
            dist: {
                files: {
                    'dist/bundle.js': ['dist/app.js']
                }
            },
            dev: {
                files: {
                    'src/main/resources/static/app.min.js': ['dist/app.js']
                }
            }
        },

        uglify: {
            options: {
                banner: '/*! <%= pkg.name %> (<%= pkg.version %>) <%= grunt.template.today("yyyy-mm-dd") %> */\n'
            },
            dist: {
                files: {
                    'src/main/resources/static/app.min.js': ['dist/bundle.js']
                }
            }
        }
    });

    // define grunt tasks
    grunt.registerTask('dev', ['babel', 'browserify:dev']);
    grunt.registerTask('dist', ['babel', 'browserify:dist', 'uglify']);

};
