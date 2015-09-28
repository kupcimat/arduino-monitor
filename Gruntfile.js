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
                    'dist/components/Index.js': 'src/main/javascript/components/Index.jsx',
                    'dist/components/LogTable.js': 'src/main/javascript/components/LogTable.jsx',
                    'dist/components/SensorReport.js': 'src/main/javascript/components/SensorReport.jsx',
                    'dist/utils/api.js': 'src/main/javascript/utils/api.js',
                    'dist/utils/util.js': 'src/main/javascript/utils/util.js'
                }
            }
        },

        browserify: {
            dist: {
                src: 'dist/**/*.js',
                dest: 'dist/bundle.js'
            },
            dev: {
                src: 'dist/**/*.js',
                dest: 'src/main/resources/static/app.min.js'
            }
        },

        uglify: {
            options: {
                banner: '/*! <%= pkg.name %> (<%= pkg.version %>) <%= grunt.template.today("yyyy-mm-dd") %> */\n'
            },
            dist: {
                src: 'dist/bundle.js',
                dest: 'src/main/resources/static/app.min.js'
            }
        }
    });

    // define grunt tasks
    grunt.registerTask('dev', ['babel', 'browserify:dev']);
    grunt.registerTask('dist', ['babel', 'browserify:dist', 'uglify']);

};
